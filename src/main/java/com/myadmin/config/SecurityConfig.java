package com.myadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 安全配置类
 * <p>
 * 配置 JWT 认证、权限控制、CORS 等安全相关设置
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * 白名单路径 - 无需认证即可访问
     */
    private static final String[] WHITE_LIST = {
            // 认证相关
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh",
            // Swagger 文档
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            // H2 控制台（仅开发环境）
            "/h2-console/**",
            // 静态资源
            "/favicon.ico",
            "/error"
    };

    /**
     * 配置安全过滤链
     *
     * @param http HttpSecurity 配置对象
     * @return SecurityFilterChain 安全过滤链
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF（使用 JWT 不需要）
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用 HTTP Basic 认证
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 使用无状态会话（JWT）
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 允许 H2 控制台使用 iframe
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                // 配置请求授权
                .authorizeHttpRequests(auth -> auth
                        // 白名单路径允许匿名访问
                        .requestMatchers(WHITE_LIST).permitAll()
                        // 其他请求需要认证
                        .anyRequest().authenticated()
                );

        // TODO: 添加 JWT 认证过滤器（在 JWT 模块实现后添加）

        return http.build();
    }

    /**
     * 密码编码器
     * <p>
     * 使用 BCrypt 算法进行密码加密
     * </p>
     *
     * @return BCryptPasswordEncoder 密码编码器实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
