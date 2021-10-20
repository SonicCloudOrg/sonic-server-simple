package com.sonic.simple.dao;

import com.sonic.simple.models.Jobs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ZhouYiXun
 * @des Job数据库操作
 * @date 2021/8/22 11:29
 */
public interface JobsRepository extends JpaRepository<Jobs, Integer> {
    Page<Jobs> findByProjectId(int projectId, Pageable pageable);
}
