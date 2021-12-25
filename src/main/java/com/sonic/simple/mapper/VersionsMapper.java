package com.sonic.simple.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sonic.simple.models.domain.Versions;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口
 * @author JayWenStar
 * @since 2021-12-17
 */
@Mapper
public interface VersionsMapper extends BaseMapper<Versions> {

}
