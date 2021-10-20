package com.sonic.simple.dao;

import com.sonic.simple.models.Elements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZhouYiXun
 * @des Elements数据库操作
 * @date 2021/8/16 20:29
 */
public interface ElementsRepository extends JpaRepository<Elements, Integer>, JpaSpecificationExecutor<Elements> {
    @Transactional
    void deleteByProjectId(int projectId);
}
