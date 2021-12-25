package com.sonic.simple.services;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sonic.simple.exception.SonicException;
import com.sonic.simple.models.domain.Jobs;
import com.sonic.simple.models.http.RespModel;

/**
 * @author ZhouYiXun
 * @des 定时任务逻辑层
 * @date 2021/8/22 11:20
 */
public interface JobsService extends IService<Jobs> {
    RespModel<String> saveJobs(Jobs jobs) throws SonicException;

    RespModel<String> updateStatus(int id, int type);

    RespModel<String> delete(int id);

    Page<Jobs> findByProjectId(int projectId, Page<Jobs> pageable);

    Jobs findById(int id);
}
