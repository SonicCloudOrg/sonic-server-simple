package org.cloud.sonic.simple.services;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.sonic.simple.models.domain.Elements;
import org.cloud.sonic.simple.models.http.RespModel;

import java.util.List;

public interface ElementsService extends IService<Elements> {
    Page<Elements> findAll(int projectId, String type, List<String> eleTypes, String name, Page<Elements> pageable);

    RespModel<String> delete(int id);

    Elements findById(int id);

    boolean deleteByProjectId(int projectId);
}
