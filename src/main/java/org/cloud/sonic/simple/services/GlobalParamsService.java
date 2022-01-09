package org.cloud.sonic.simple.services;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.sonic.simple.models.domain.GlobalParams;

import java.util.List;

public interface GlobalParamsService extends IService<GlobalParams> {
    List<GlobalParams> findAll(int projectId);

    boolean delete(int id);

    GlobalParams findById(int id);

    boolean deleteByProjectId(int projectId);


}
