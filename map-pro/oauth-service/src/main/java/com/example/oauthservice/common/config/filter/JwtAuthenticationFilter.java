//package com.example.j.common.config.filter;
//
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.toolkit.IdWorker;
//import com.example.j.common.exception.ParameterException;
//import com.example.j.common.util.Assert;
//import com.example.j.common.util.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import org.slf4j.MDC;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private static final String TOKEN = "token";
//    private final HttpServletRequest autowiredRequest;
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    @NotNull HttpServletResponse response,
//                                    @NotNull FilterChain filterChain
//    ) throws ServletException, IOException {
//        String path = request.getServletPath();
//
//        if (path.startsWith("/api")) {
//            MDC.put("traceId", String.valueOf(IdWorker.getId()));
//            log.info("{}: {}", request.getMethod(), path);
//        }
//
//        String jwtToken = request.getHeader(TOKEN);
//
//        checkToken(jwtToken);
//
//        filterChain.doFilter(request, response);
//    }
//
//    private void checkToken(String token) {
//        String id;
//        try {
//            id = JwtUtil.parseJwt(token).getSubject();
//        } catch (Exception e) {
//            throw new ParameterException("token非法");
//        }
//
//        Object UserObject = redisTemplate.opsForValue().get(STR."login:\{id}");
//        Assert.notNull(UserObject, "用户状态发生改变，请重新登录");
//
//        LoginUser user;
//        try {
//            user = JSON.parseObject(UserObject.toString(), LoginUser.class);
//        } catch (Exception e) {
//            log.error("用户JSON 解析异常", e);
//            throw new ParameterException("用户信息解析错误");
//        }
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//    }
//
//    /**
//     * true: 正常
//     * false: redis或者token异常
//     * @return
//     */
//    public Boolean checkLoginStatus() {
//        String jwtToken = autowiredRequest.getHeader(TOKEN);
//        if (!StringUtils.hasLength(jwtToken)) {
//            return false;
//        }
//        try {
//            checkToken(jwtToken);
//            return true;
//        } catch (Exception e) {
//            log.warn("用户状态异常", e);
//            return false;
//        }
//    }
//
//}
