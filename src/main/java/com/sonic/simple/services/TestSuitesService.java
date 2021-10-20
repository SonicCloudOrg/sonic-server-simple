package com.sonic.simple.services;


import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.models.TestSuites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ZhouYiXun
 * @des 测试套件逻辑层
 * @date 2021/8/20 17:51
 */
public interface TestSuitesService {
    RespModel runSuite(int id, String strike);

    TestSuites findById(int id);

    boolean delete(int id);

    void save(TestSuites testSuites);

    Page<TestSuites> findByProjectId(int projectId, String name, Pageable pageable);

    List<TestSuites> findByProjectId(int projectId);
}
