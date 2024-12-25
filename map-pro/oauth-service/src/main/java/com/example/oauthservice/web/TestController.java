
package com.example.oauthservice.web;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
public class TestController {

    @GetMapping("/test01")
        @PreAuthorize("hasAuthority('SCOPE_message.read')")
    public String test01() {
        return "test01";
    }

    @GetMapping("/test02")
    @PreAuthorize("hasAuthority('SCOPE_message.write')")
    public String test02() {
        return "test02";
    }

    @ResponseBody
    @GetMapping("/getCaptcha")
    public Map<String,Object> getCaptcha(HttpSession session) {
        // 使用hutool-captcha生成图形验证码
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150, 40, 4, 2);
        // 这里应该返回一个统一响应类，暂时使用map代替
        Map<String,Object> result = new HashMap<>();
        result.put("code", HttpStatus.OK.value());
        result.put("success", true);
        result.put("message", "获取验证码成功.");
        result.put("data", captcha.getImageBase64Data());
        // 存入session中
        session.setAttribute("captcha", captcha.getCode());
        return result;
    }

    @ResponseBody
    @GetMapping("/user")
    public Map<String,Object> user(Principal principal) {
        if (!(principal instanceof JwtAuthenticationToken token)) {
            return Collections.emptyMap();
        }
        return token.getToken().getClaims();
    }

}
