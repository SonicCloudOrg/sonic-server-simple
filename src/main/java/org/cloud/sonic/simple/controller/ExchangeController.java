package org.cloud.sonic.simple.controller;

import com.alibaba.fastjson.JSONObject;
import org.cloud.sonic.simple.config.WebAspect;
import org.cloud.sonic.simple.models.domain.Agents;
import org.cloud.sonic.simple.models.domain.Devices;
import org.cloud.sonic.simple.models.http.RespEnum;
import org.cloud.sonic.simple.models.http.RespModel;
import org.cloud.sonic.simple.netty.NettyServer;
import org.cloud.sonic.simple.services.AgentsService;
import org.cloud.sonic.simple.services.DevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transport/exchange")
public class ExchangeController {

    @Autowired
    private DevicesService devicesService;
    @Autowired
    private AgentsService agentsService;

    @WebAspect
    @GetMapping("/reboot")
    public RespModel<String> reboot(@RequestParam(name = "id") int id) {
        Devices device = devicesService.findById(id);
        if (device != null) {
            Agents agent = agentsService.findById(device.getAgentId());
            if (agent != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg", "reboot");
                jsonObject.put("udId", device.getUdId());
                jsonObject.put("platform", device.getPlatform());
                NettyServer.getMap().get(agent.getId()).writeAndFlush(jsonObject.toJSONString());
                return new RespModel<>(2000, "发送成功！");
            } else {
                return new RespModel<>(RespEnum.ID_NOT_FOUND);
            }
        } else {
            return new RespModel<>(RespEnum.ID_NOT_FOUND);
        }
    }

    @WebAspect
    @GetMapping("/stop")
    public RespModel<String> stop(@RequestParam(name = "id") int id) {
        Agents agent = agentsService.findById(id);
        if (agent != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "stop");
            NettyServer.getMap().get(agent.getId()).writeAndFlush(jsonObject.toJSONString());
            return new RespModel<>(2000, "发送成功！");
        } else {
            return new RespModel<>(RespEnum.ID_NOT_FOUND);
        }
    }
}