package com.myadmin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI (Swagger) 配置类
 * <p>
 * 配置 API 文档的基本信息和安全认证方式
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Configuration
public class OpenApiConfig {

    /**
     * 安全方案名称
     */
    private static final String SECURITY_SCHEME_NAME = "Bearer Token";

    /**
     * 配置 OpenAPI 文档
     *
     * @return OpenAPI 配置对象
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // API 基本信息
                .info(new Info()
                        .title("MyAdmin API")
                        .description("企业级项目开发基础套件 API 文档\n\n" +
                                "## 功能模块\n" +
                                "- 用户认证与授权（JWT）\n" +
                                "- 用户管理\n" +
                                "- 角色管理\n" +
                                "- 菜单管理\n" +
                                "- 权限管理\n\n" +
                                "## 认证方式\n" +
                                "使用 JWT Bearer Token 进行认证，请在请求头中添加：\n" +
                                "```\n" +
                                "Authorization: Bearer <your_token>\n" +
                                "```")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MyAdmin Team")
                                .email("admin@myadmin.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                // 安全方案
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("请输入 JWT Token")));
    }
}
