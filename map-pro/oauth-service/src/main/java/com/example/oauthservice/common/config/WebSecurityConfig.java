package com.example.oauthservice.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

//
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
//
//    private final AuthenticationEntryPointImpl authenticationEntryPoint;
//    private final AccessDeniedHandlerImpl accessDeniedHandler;
//    private final UserDetailsService userDetailsService;
////    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public SecurityWebFilterChain web(ServerHttpSecurity http) throws Exception {
//        http
//                // Demonstrate that method security works
//                // Best practice to use both for defense in depth
//                .authorizeExchange((authorize) -> authorize
//                        .anyExchange().permitAll()
//                )
//                .httpBasic(withDefaults());
    // @formatter:on
//        return http.build();
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

    /// /        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
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
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//                .authorizeHttpRequests((authorize) ->
//                        authorize.requestMatchers(
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
//                )
//                .httpBasic(withDefaults())
//                .formLogin(withDefaults());
//        // @formatter:on
//        return http.build();
//    }

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

    /**
     * 密码编码器
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    /**
//     * 加载用户信息
//     */
//    @Bean
//    MapReactiveUserDetailsService userDetailsService() {
//        // @formatter:off
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN", "USER")
//                .build();
//        // @formatter:on
//        return new MapReactiveUserDetailsService(user, admin);
//    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userDetailsService;
//    }
}
