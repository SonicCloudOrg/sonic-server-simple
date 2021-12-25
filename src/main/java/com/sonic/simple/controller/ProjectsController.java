package com.sonic.simple.controller;

import com.sonic.simple.config.WebAspect;
import com.sonic.simple.exception.SonicException;
import com.sonic.simple.models.base.TypeConverter;
import com.sonic.simple.models.domain.Projects;
import com.sonic.simple.models.dto.ProjectsDTO;
import com.sonic.simple.models.http.RespEnum;
import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.services.ProjectsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhouYiXun
 * @des
 * @date 2021/9/9 22:46
 */
@Api(tags = "项目管理相关")
@RestController
@RequestMapping("/api/controller/projects")
public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @WebAspect
    @ApiOperation(value = "更新项目信息", notes = "新增或更新项目信息")
    @PutMapping
    public RespModel<String> save(@Validated @RequestBody ProjectsDTO projects) {
        projectsService.save(projects.convertTo());
        return new RespModel<>(RespEnum.UPDATE_OK);
    }

    @WebAspect
    @ApiOperation(value = "查找所有项目", notes = "查找所有项目列表")
    @GetMapping("/list")
    public RespModel<List<ProjectsDTO>> findAll() {
        return new RespModel<>(
                RespEnum.SEARCH_OK,
                projectsService.findAll().stream().map(TypeConverter::convertTo).collect(Collectors.toList())
        );
    }

    @WebAspect
    @ApiOperation(value = "查询项目信息", notes = "查找对应id下的详细信息")
    @ApiImplicitParam(name = "id", value = "项目id", dataTypeClass = Integer.class)
    @GetMapping
    public RespModel<?> findById(@RequestParam(name = "id") int id) {
        Projects projects = projectsService.findById(id);
        if (projects != null) {
            return new RespModel<>(RespEnum.SEARCH_OK, projects.convertTo());
        } else {
            return new RespModel<>(RespEnum.ID_NOT_FOUND);
        }
    }

    @WebAspect
    @ApiOperation(value = "删除", notes = "删除对应id下的详细信息")
    @ApiImplicitParam(name = "id", value = "项目id", dataTypeClass = Integer.class)
    @DeleteMapping
    public RespModel<String> delete(@RequestParam(name = "id") int id) throws SonicException {
        projectsService.delete(id);
        return new RespModel<>(RespEnum.DELETE_OK);
    }
}
