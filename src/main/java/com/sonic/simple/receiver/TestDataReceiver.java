package com.sonic.simple.receiver;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class TestDataReceiver {
    private final Logger logger = LoggerFactory.getLogger(TestDataReceiver.class);
    @Autowired
    private AgentsService agentsService;
    @Autowired
    private DevicesService devicesService;
    @Autowired
    private ResultsService resultsService;
    @Autowired
    private TestCasesService testCasesService;
    @Autowired
    private ResultDetailService resultDetailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "TestDataQueue")
    public void process(JSONObject jsonMsg, Channel channel, Message message) throws Exception {
        logger.info("TestDataQueue消费者收到消息  : " + jsonMsg.toString());
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        switch (jsonMsg.getString("msg")) {
            case "auth":
                int i = agentsService.auth(jsonMsg.getString("key"));
                if (i != 0) {
                    JSONObject auth = new JSONObject();
                    auth.put("msg", "auth");
                    auth.put("id", i);
                    rabbitTemplate.convertAndSend("MsgDirectExchange", jsonMsg.getString("key"), auth);
                }
                break;
            case "agentInfo":
                jsonMsg.remove("msg");
                agentsService.save(jsonMsg);
                break;
            case "offLine":
                agentsService.offLine(jsonMsg.getInteger("agentId"));
                break;
            case "subResultCount":
                resultsService.subResultCount(jsonMsg.getInteger("rid"));
                break;
            case "deviceDetail":
                devicesService.deviceStatus(jsonMsg);
                break;
//            case "elapsed":
//                jsonMsg.remove("msg");
//                controllerResp = controllerFeignClient.saveElapsed(jsonMsg);
//                break;
            case "step":
            case "perform":
            case "record":
            case "status":
                resultDetailService.saveByTransport(jsonMsg);
                break;
            case "findSteps":
                JSONObject j = testCasesService.findSteps(jsonMsg.getInteger("caseId"));
                if (j != null) {
                    JSONObject steps = new JSONObject();
                    steps.put("msg", "runStep");
                    steps.put("pf", j.get("pf"));
                    steps.put("steps", j.get("steps"));
                    steps.put("gp", j.get("gp"));
                    steps.put("sessionId", jsonMsg.getString("sessionId"));
                    steps.put("pwd", jsonMsg.getString("pwd"));
                    steps.put("udId", jsonMsg.getString("udId"));
                    rabbitTemplate.convertAndSend("MsgDirectExchange", jsonMsg.getString("key"), steps);
                }
                break;
        }
        channel.basicAck(deliveryTag, true);
    }
}