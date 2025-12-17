package com.myadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA 配置类
 * <p>
 * 启用 JPA 审计功能，自动填充创建时间和更新时间
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.myadmin.modules")
public class JpaConfig {
    // JPA 配置类，启用审计功能
    // 审计功能会自动填充 @CreatedDate 和 @LastModifiedDate 注解的字段
}
