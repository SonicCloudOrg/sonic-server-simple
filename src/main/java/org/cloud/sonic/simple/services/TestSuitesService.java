package org.cloud.sonic.simple.services;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.sonic.simple.models.base.CommentPage;
import org.cloud.sonic.simple.models.domain.TestSuites;
import org.cloud.sonic.simple.models.dto.StepsDTO;
import org.cloud.sonic.simple.models.dto.TestSuitesDTO;
import org.cloud.sonic.simple.models.http.RespModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ZhouYiXun
 * @des 测试套件逻辑层
 * @date 2021/8/20 17:51
 */
public interface TestSuitesService extends IService<TestSuites> {
    RespModel<String> runSuite(int id, String strike);

    RespModel<String> forceStopSuite(int id, String strike);

    TestSuitesDTO findById(int id);

    @Transactional
    JSONObject getStep(StepsDTO steps);

    boolean delete(int id);

    void saveTestSuites(TestSuitesDTO testSuitesDTO);

    CommentPage<TestSuitesDTO> findByProjectId(int projectId, String name, Page<TestSuites> pageable);

    List<TestSuitesDTO> findByProjectId(int projectId);

    boolean deleteByProjectId(int projectId);

    List<TestSuites> listTestSuitesByTestCasesId(int testCasesId);
}
