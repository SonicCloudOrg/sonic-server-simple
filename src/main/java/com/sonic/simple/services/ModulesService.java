package com.sonic.simple.services;

import com.sonic.simple.models.Modules;

import java.util.List;

public interface ModulesService {
    void save(Modules modules);

    boolean delete(int id);

    List<Modules> findByProjectId(int projectId);

    Modules findById(int id);
}
