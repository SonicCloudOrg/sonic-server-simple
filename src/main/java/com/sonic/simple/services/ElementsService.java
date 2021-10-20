package com.sonic.simple.services;

import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.models.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ElementsService {
    Page<Elements> findAll(int projectId, String type, List<String> eleTypes, String name, Pageable pageable);

    RespModel delete(int id);

    boolean save(Elements elements);

    Elements findById(int id);
}
