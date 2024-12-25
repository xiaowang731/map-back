package com.example.fs.common.exception.handler;

import com.example.fs.common.JsonResponse;

import com.alibaba.fastjson.JSON;
import com.example.fs.common.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author Adss
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler  {

    // 用户权限问题
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {
        JsonResponse<Object> message = new JsonResponse<>()
                .code(JsonResponse.PERMISSIONS_ERROR)
                .message("访问权限不足！");
        String json = JSON.toJSONString(message);
        WebUtils.renderString(response, json);
    }
}
