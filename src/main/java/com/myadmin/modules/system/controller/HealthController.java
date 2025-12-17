package com.myadmin.modules.system.controller;

import com.myadmin.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查控制器
 * <p>
 * 提供系统健康检查和基本信息接口
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Tag(name = "系统健康检查", description = "系统健康检查和基本信息接口")
@RestController
@RequestMapping("/api/health")
public class HealthController {

    /**
     * 健康检查接口
     *
     * @return 健康状态信息
     */
    @Operation(summary = "健康检查", description = "检查系统是否正常运行")
    @GetMapping
    public Result<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "UP");
        data.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        data.put("version", "1.0.0");
        data.put("name", "MyAdmin");
        return Result.success("系统运行正常", data);
    }

    /**
     * 系统信息接口
     *
     * @return 系统信息
     */
    @Operation(summary = "系统信息", description = "获取系统基本信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> info() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "MyAdmin");
        data.put("description", "企业级项目开发基础套件");
        data.put("version", "1.0.0");
        data.put("javaVersion", System.getProperty("java.version"));
        data.put("osName", System.getProperty("os.name"));
        data.put("osArch", System.getProperty("os.arch"));
        return Result.success(data);
    }
}
