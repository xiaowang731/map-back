//package com.example.j.common.config;
//
//
//import com.example.j.common.exception.handler.AccessDeniedHandlerImpl;
//import com.example.j.common.exception.handler.AuthenticationEntryPointImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@EnableWebSecurity
//@Configuration
//@RequiredArgsConstructor
//// @EnableWebFluxSecurity
//public class SecurityConfig {
//
//    private final AuthenticationEntryPointImpl authenticationEntryPoint;
//    private final AccessDeniedHandlerImpl accessDeniedHandler;
//    private final UserDetailsService userDetailsService;
////    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize ->
//                // 接口放行
//                authorize.requestMatchers(
//                                "/api/v1/login",
//                                "/swagger-ui/**",
//                                "/doc.html",
//                                "/webjars/**",
//                                "/v2/**",
//                                "/v3/**",
//                                "/swagger-resources/**",
//                                "/files/**",
//                                "/api/v1/code-img/*"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//        );
//        http.authenticationProvider(authenticationProvider());
//
//        http.formLogin(AbstractHttpConfigurer::disable)
//                .logout(AbstractHttpConfigurer::disable)
//                .sessionManagement(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable);
//
////        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http.exceptionHandling(exceptionHandling ->
//                exceptionHandling
//                        .accessDeniedHandler(accessDeniedHandler)
//                        .authenticationEntryPoint(authenticationEntryPoint)
//        );
//        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
//
//        return http.build();
//    }
//
//    /**
//     * 跨域配置
//     */
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.addAllowedOriginPattern("*"); // 允许任何源
//        corsConfig.addAllowedMethod("*"); // 允许任何HTTP方法
//        corsConfig.addAllowedHeader("*"); // 允许任何HTTP头
//        corsConfig.setAllowCredentials(true); // 允许证书（cookies）
//        corsConfig.setMaxAge(3600L); // 预检请求的缓存时间（秒）
//        corsConfig.addExposedHeader("Content-Disposition");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig); // 对所有路径应用这个配置
//        return source;
//    }
//
//    /**
//     * 身份验证管理器
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    /**
//     * 处理身份验证端点
//     */
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return daoAuthenticationProvider;
//    }
//
//    /**
//     * 密码编码器
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 加载用户信息
//     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userDetailsService;
//    }
//}
