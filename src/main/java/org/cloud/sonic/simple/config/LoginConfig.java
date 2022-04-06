package org.cloud.sonic.simple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class LoginConfig extends WebMvcConfigurationSupport {

    @Bean
    public AdminInterceptor getAdminInterceptor() {
        return new AdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(getAdminInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/**/users/login", "/**/users/loginConfig","/**/users/register","/**/swagger-resources"
                ,"/**/v2/api-docs","/**/folder/upload/**","/**/keepFiles/**","/**/imageFiles/**"
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
