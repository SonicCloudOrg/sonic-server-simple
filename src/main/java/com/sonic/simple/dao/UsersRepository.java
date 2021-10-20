package com.sonic.simple.dao;

import com.sonic.simple.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUserName(String username);
}
