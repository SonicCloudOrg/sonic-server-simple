package com.sonic.simple.services;

import com.sonic.simple.exception.SonicException;
import com.sonic.simple.models.Jobs;
import com.sonic.simple.models.http.RespModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author ZhouYiXun
 * @des 定时任务逻辑层
 * @date 2021/8/22 11:20
 */
public interface JobsService {
    RespModel save(Jobs jobs) throws SonicException;

    RespModel updateStatus(int id, int type);

    RespModel delete(int id);

    Page<Jobs> findByProjectId(int projectId, Pageable pageable);

    Jobs findById(int id);
}
