package com.example.oauthservice.authentication.handler;

import com.example.oauthservice.Result;
import com.example.oauthservice.util.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Adss
 * @data 2024/12/26 15:09
 */
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    // private final String loginPageUri;
    //
    // private final AuthenticationSuccessHandler authenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    //
    // @Override
    // @SneakyThrows
    // public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    //     // 如果是绝对路径(前后端分离)
    //     if (UrlUtils.isAbsoluteUrl(this.loginPageUri)) {
    //         Result<String> success = Result.success();
    //         response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    //         response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    //         response.getWriter().write(JsonUtils.objectCovertToJson(success));
    //         response.getWriter().flush();
    //     } else {
    //         authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    //     }
    // }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Result<String> success = Result.success();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtils.objectCovertToJson(success));
        response.getWriter().flush();
    }



}