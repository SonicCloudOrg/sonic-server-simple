package com.sonic.simple.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class LoginConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new AdminInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/**/users/login","/**/users/register","/**/swagger-resources"
                ,"/**/v2/api-docs","/**/folder/upload","/**/folder/recordFiles/**","/**/keepFiles/**","/**/imageFiles/**"
                ,"/**/recordFiles/**","/**/logFiles/**","/**/packageFiles/**");
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/api/folder/keepFiles/**")
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/keepFiles/");
        registry.addResourceHandler("/api/folder/imageFiles/**")
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/imageFiles/");
        registry.addResourceHandler("/api/folder/recordFiles/**")
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/recordFiles/");
        registry.addResourceHandler("/api/folder/logFiles/**")
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/logFiles/");
        registry.addResourceHandler("/api/folder/packageFiles/**")
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/packageFiles/");
        super.addResourceHandlers(registry);
    }
}