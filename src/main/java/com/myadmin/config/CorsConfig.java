package com.myadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * CORS 跨域配置类
 * <p>
 * 配置允许跨域访问的规则，支持前后端分离架构
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Configuration
public class CorsConfig {

    /**
     * 配置 CORS 过滤器
     *
     * @return CorsFilter CORS 过滤器实例
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 允许的源（生产环境应配置具体域名）
        config.setAllowedOriginPatterns(Arrays.asList("*"));

        // 允许的请求头
        config.setAllowedHeaders(Arrays.asList("*"));

        // 允许的请求方法
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // 允许携带凭证（cookies）
        config.setAllowCredentials(true);

        // 暴露的响应头（前端可以获取）
        config.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // 预检请求缓存时间（秒）
        config.setMaxAge(3600L);

        // 配置 URL 路径映射
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
