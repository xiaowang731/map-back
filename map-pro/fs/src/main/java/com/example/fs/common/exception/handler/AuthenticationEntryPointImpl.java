package com.example.fs.common.exception.handler;

import com.example.fs.common.JsonResponse;

import com.alibaba.fastjson.JSON;
import com.example.fs.common.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


/**
 * @author Adss
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    // 用户认证问题
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {
        JsonResponse<Object> message = new JsonResponse<>()
                .code(JsonResponse.ACCOUNT_ERROR)
                .message("身份认证信息异常，请重新登录");
        String json = JSON.toJSONString(message);
        WebUtils.renderString(response, json);
    }
}
