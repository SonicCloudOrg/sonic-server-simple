package com.sonic.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author ZhouYiXun
 * @des 启动类
 * @date 2021/8/15 19:56
 */
@SpringBootApplication
@EnableJpaAuditing
public class SimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}
