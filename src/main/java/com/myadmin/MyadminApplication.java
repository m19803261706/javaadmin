package com.myadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MyAdmin 应用程序主启动类
 * <p>
 * 企业级项目开发基础套件，包含以下核心功能：
 * <ul>
 *     <li>用户认证与授权（JWT）</li>
 *     <li>用户管理</li>
 *     <li>角色管理</li>
 *     <li>菜单管理（精确到按钮级别）</li>
 *     <li>权限管理</li>
 * </ul>
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@SpringBootApplication
public class MyadminApplication {

    /**
     * 应用程序入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(MyadminApplication.class, args);
    }

}
