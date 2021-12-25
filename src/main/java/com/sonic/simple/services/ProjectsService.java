package com.sonic.simple.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sonic.simple.exception.SonicException;
import com.sonic.simple.models.domain.Projects;

import java.util.List;

/**
 * @author ZhouYiXun
 * @des 项目逻辑层
 * @date 2021/8/20 20:51
 */
public interface ProjectsService extends IService<Projects> {

    Projects findById(int id);

    List<Projects> findAll();

    void delete(int id) throws SonicException;
}
