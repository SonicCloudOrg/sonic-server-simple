package com.sonic.simple.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sonic.simple.models.domain.Agents;
import com.sonic.simple.models.domain.Devices;
import com.sonic.simple.models.domain.Users;
import com.sonic.simple.models.interfaces.AgentStatus;
import com.sonic.simple.services.*;
import com.sonic.simple.tools.SpringTool;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private AgentsService agentsService = SpringTool.getBean(AgentsService.class);
    private DevicesService devicesService = SpringTool.getBean(DevicesService.class);
    private ResultsService resultsService = SpringTool.getBean(ResultsService.class);
    private TestCasesService testCasesService = SpringTool.getBean(TestCasesService.class);
    private ResultDetailService resultDetailService = SpringTool.getBean(ResultDetailService.class);
    private UsersService usersService = SpringTool.getBean(UsersService.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("Agent：{} 连接到服务器!", ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        JSONObject jsonMsg = JSON.parseObject((String) msg);
        logger.info("服务器收到Agent: {} 消息: {}", ctx.channel().remoteAddress(), jsonMsg);
        switch (jsonMsg.getString("msg")) {
            case "temperature": {
                devicesService.refreshDevicesTemper(jsonMsg);
                break;
            }
            case "debugUser":
                Users users = usersService.getUserInfo(jsonMsg.getString("token"));
                Devices devices = devicesService.findByAgentIdAndUdId(jsonMsg.getInteger("agentId"),
                        jsonMsg.getString("udId"));
                devices.setUser(users.getUserName());
                devicesService.save(devices);
                break;
            case "heartBeat":
                Agents agentsOnline = agentsService.findById(jsonMsg.getInteger("agentId"));
                if (agentsOnline.getStatus() != (AgentStatus.ONLINE)) {
                    agentsOnline.setStatus(AgentStatus.ONLINE);
                    agentsService.save(agentsOnline);
                }
                break;
            case "agentInfo":
                if (NettyServer.getMap().get(jsonMsg.getInteger("agentId")) != null) {
                    NettyServer.getMap().get(jsonMsg.getInteger("agentId")).close();
                    NettyServer.getMap().remove(jsonMsg.getInteger("agentId"));
                }
                NettyServer.getMap().put(jsonMsg.getInteger("agentId"), ctx.channel());
                jsonMsg.remove("msg");
                agentsService.saveAgents(jsonMsg);
                break;
            case "subResultCount":
                resultsService.subResultCount(jsonMsg.getInteger("rid"));
                break;
            case "deviceDetail":
                devicesService.deviceStatus(jsonMsg);
                break;
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
                    NettyServer.getMap().get(jsonMsg.getInteger("agentId")).writeAndFlush(steps.toJSONString());
                }
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("Agent: {} 发生异常 {}", ctx.channel().remoteAddress(), cause.fillInStackTrace());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Agent: {} 连接断开", ctx.channel().remoteAddress());
        for (Map.Entry<Integer, Channel> entry : NettyServer.getMap().entrySet()) {
            if (entry.getValue().equals(ctx.channel())) {
                int agentId = entry.getKey();
                agentsService.offLine(agentId);
            }
        }
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                logger.info("Agent: {} 无心跳！关闭连接...", ctx.channel().remoteAddress());
                for (Map.Entry<Integer, Channel> entry : NettyServer.getMap().entrySet()) {
                    if (entry.getValue().equals(ctx.channel())) {
                        int agentId = entry.getKey();
                        agentsService.offLine(agentId);
                    }
                }
                ctx.close();
            }
            if (event.state().equals(IdleState.ALL_IDLE)) {
                logger.info("Agent: {} 读写空闲，发送心跳检测...", ctx.channel().remoteAddress());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg", "heartBeat");
                ctx.channel().writeAndFlush(jsonObject.toJSONString());
            }
        }
    }
}