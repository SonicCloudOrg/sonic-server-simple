package com.sonic.simple.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author ZhouYiXun
 * @des 静态资源重定向配置
 * @date 2021/8/18 20:26
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/api/folder/keepFiles/**")
//                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/keepFiles/");
//        registry.addResourceHandler("/api/folder/imageFiles/**")
//                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/imageFiles/");
//        registry.addResourceHandler("/api/folder/recordFiles/**")
//                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/recordFiles/");
//        registry.addResourceHandler("/api/folder/logFiles/**")
//                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/logFiles/");
//        super.addResourceHandlers(registry);
//    }
}