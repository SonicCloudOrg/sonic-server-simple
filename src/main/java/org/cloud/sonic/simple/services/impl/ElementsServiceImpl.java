package org.cloud.sonic.simple.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cloud.sonic.simple.mapper.ElementsMapper;
import org.cloud.sonic.simple.models.domain.Elements;
import org.cloud.sonic.simple.models.domain.Steps;
import org.cloud.sonic.simple.models.http.RespEnum;
import org.cloud.sonic.simple.models.http.RespModel;
import org.cloud.sonic.simple.services.ElementsService;
import org.cloud.sonic.simple.services.StepsService;
import org.cloud.sonic.simple.services.impl.base.SonicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElementsServiceImpl extends SonicServiceImpl<ElementsMapper, Elements> implements ElementsService {

    @Autowired
    private ElementsMapper elementsMapper;
    @Autowired
    private StepsService stepsService;

    @Override
    public Page<Elements> findAll(int projectId, String type, List<String> eleTypes, String name, Page<Elements> pageable) {
        LambdaQueryChainWrapper<Elements> lambdaQuery = lambdaQuery();

        if (type != null && type.length() > 0) {
            switch (type) {
                case "normal" -> lambdaQuery.and(
                        l -> l.ne(Elements::getEleType, "point").ne(Elements::getEleType, "image")
                );
                case "point" -> lambdaQuery.eq(Elements::getEleType, "point");
                case "image" -> lambdaQuery.eq(Elements::getEleType, "image");
            }
        }

        if (eleTypes != null) {
            lambdaQuery.in(Elements::getEleType, eleTypes);
        }
        if (name != null && name.length() > 0) {
            lambdaQuery.like(Elements::getEleName, name);
        }

        lambdaQuery.eq(Elements::getProjectId, projectId);
        lambdaQuery.orderByDesc(Elements::getId);

        return lambdaQuery.page(pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespModel delete(int id) {
        if (existsById(id)) {
            List<Steps> stepsList = stepsService.listStepsByElementsId(id);
            if (stepsList.size() > 0) {
                return new RespModel(RespEnum.DELETE_ERROR, stepsList);
            } else {
                baseMapper.deleteById(id);
                return new RespModel<>(RespEnum.DELETE_OK);
            }
        } else {
            return new RespModel<>(RespEnum.ID_NOT_FOUND);
        }
    }

    @Override
    public Elements findById(int id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean deleteByProjectId(int projectId) {
        return baseMapper.delete(new LambdaQueryWrapper<Elements>().eq(Elements::getProjectId, projectId)) > 0;
    }
}
