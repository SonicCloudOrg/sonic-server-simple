package com.sonic.simple.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.sonic.simple.dao.ResultDetailRepository;
import com.sonic.simple.models.Devices;
import com.sonic.simple.models.ResultDetail;
import com.sonic.simple.services.DevicesService;
import com.sonic.simple.services.ResultDetailService;
import com.sonic.simple.services.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhouYiXun
 * @des 测试结果详情逻辑实现
 * @date 2021/8/21 20:55
 */
@Service
public class ResultDetailServiceImpl implements ResultDetailService {
    @Autowired
    private ResultDetailRepository resultDetailRepository;
    @Autowired
    private DevicesService devicesService;
    @Autowired
    private ResultsService resultsService;

    @Override
    public void save(ResultDetail resultDetail) {
        resultDetailRepository.save(resultDetail);
    }

    @Override
    public void saveByTransport(JSONObject jsonMsg) {
        Devices resultDevice = devicesService.findByAgentIdAndUdId(jsonMsg.getInteger("agentId")
                , jsonMsg.getString("udId"));
        ResultDetail resultInfo = new ResultDetail();
        resultInfo.setType(jsonMsg.getString("msg"));
        resultInfo.setLog(jsonMsg.getString("log"));
        resultInfo.setDes(jsonMsg.getString("des"));
        resultInfo.setStatus(jsonMsg.getInteger("status"));
        resultInfo.setResultId(jsonMsg.getInteger("rid"));
        resultInfo.setCaseId(jsonMsg.getInteger("cid"));
        resultInfo.setTime(jsonMsg.getDate("time"));
        resultInfo.setDeviceId(resultDevice == null ? 0 : resultDevice.getId());
        save(resultInfo);
        if (jsonMsg.getString("msg").equals("status")) {
            resultsService.suiteResult(jsonMsg.getInteger("rid"));
        }
    }

    @Override
    public Page<ResultDetail> findAll(int resultId, int caseId, String type, int deviceId, Pageable pageable) {
        Specification<ResultDetail> spc = (root, query, cb) -> {
            List<Order> orders = new ArrayList<>();
            orders.add(cb.asc(root.get("time")));
            query.orderBy(orders);
            List<Predicate> predicateList = new ArrayList<>();
            if (resultId != 0) {
                predicateList.add(cb.and(cb.equal(root.get("resultId"), resultId)));
            }
            if (caseId != 0) {
                predicateList.add(cb.and(cb.equal(root.get("caseId"), caseId)));
            }
            if (type != null && type.length() > 0) {
                predicateList.add(cb.and(cb.equal(root.get("type"), type)));
            }
            if (deviceId != 0) {
                predicateList.add(cb.and(cb.equal(root.get("deviceId"), deviceId)));
            }
            Predicate[] p = new Predicate[predicateList.size()];
            return query.where(predicateList.toArray(p)).getRestriction();
        };
        return resultDetailRepository.findAll(spc, pageable);
    }

    @Override
    public List<ResultDetail> findAll(int resultId, int caseId, String type, int deviceId) {
        Specification<ResultDetail> spc = (root, query, cb) -> {
            List<Order> orders = new ArrayList<>();
            orders.add(cb.asc(root.get("time")));
            query.orderBy(orders);
            List<Predicate> predicateList = new ArrayList<>();
            if (resultId != 0) {
                predicateList.add(cb.and(cb.equal(root.get("resultId"), resultId)));
            }
            if (caseId != 0) {
                predicateList.add(cb.and(cb.equal(root.get("caseId"), caseId)));
            }
            if (type != null && type.length() > 0) {
                predicateList.add(cb.and(cb.equal(root.get("type"), type)));
            }
            if (deviceId != 0) {
                predicateList.add(cb.and(cb.equal(root.get("deviceId"), deviceId)));
            }
            Predicate[] p = new Predicate[predicateList.size()];
            return query.where(predicateList.toArray(p)).getRestriction();
        };
        return resultDetailRepository.findAll(spc);
    }


    @Override
    public void deleteByResultId(int resultId) {
        resultDetailRepository.deleteByResultId(resultId);
    }
}
