package com.sonic.simple.services;

import com.alibaba.fastjson.JSONObject;
import com.sonic.simple.models.Agents;

import java.util.List;

/**
 * @author ZhouYiXun
 * @des Agent逻辑层
 * @date 2021/8/19 22:51
 */
public interface AgentsService {
    List<Agents> findAgents();

    void updateName(int id, String name);

    void save(JSONObject agents);

    void save(Agents agents);

    boolean offLine(int id);

    int auth(String key);

    String findKeyById(int id);

    Agents findById(int id);
}