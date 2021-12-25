package com.sonic.simple;

import com.sonic.simple.tools.SpringTool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author ZhouYiXun
 * @des 启动类
 * @date 2021/8/15 19:56
 */
@SpringBootApplication
@Import(SpringTool.class)
@MapperScan(basePackages = {
        "com.sonic.simple.mapper",
        "com.gitee.sunchenbin.mybatis.actable.dao.*"
})
@ComponentScan(basePackages = {"com.sonic.simple.*", "com.gitee.sunchenbin.mybatis.actable.manager.*"})
public class SimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}
