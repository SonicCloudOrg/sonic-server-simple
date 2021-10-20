package com.sonic.simple.dao;

import com.sonic.simple.models.Agents;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ZhouYiXun
 * @des Agent数据库操作
 * @date 2021/8/16 20:29
 */
public interface AgentsRepository extends JpaRepository<Agents, Integer> {
    Agents findBySecretKey(String key);
}
