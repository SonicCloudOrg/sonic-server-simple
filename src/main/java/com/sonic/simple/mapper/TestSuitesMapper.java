package com.sonic.simple.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sonic.simple.models.domain.TestSuites;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JayWenStar
 * @since 2021-12-17
 */
@Mapper
public interface TestSuitesMapper extends BaseMapper<TestSuites> {

}