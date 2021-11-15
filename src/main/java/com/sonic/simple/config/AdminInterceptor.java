package com.sonic.simple.config;

import com.alibaba.fastjson.JSONObject;
import com.sonic.simple.models.http.RespEnum;
import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.tools.JWTTokenTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTTokenTool jwtTokenTool;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().equals("/doc.html")
                || request.getRequestURI().equals("/favicon.ico")
                || request.getRequestURI().contains("css")
                || request.getRequestURI().contains("js")) {
            return true;
        }
        String token = request.getHeader("SonicToken");
        if (token == null) {
            sendJson(response);
            return false;
        }
        // 验证 token
        if (!jwtTokenTool.verify(token)) {
            sendJson(response);
            return false;
        }
        return true;
    }

    public void sendJson(HttpServletResponse response) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSON(new RespModel(RespEnum.UNAUTHORIZED)));
        } catch (Exception e) {

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}