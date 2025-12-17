package com.myadmin.common.result;

import lombok.Getter;

/**
 * 状态码枚举类
 * <p>
 * 定义系统中所有的状态码和对应消息
 * </p>
 *
 * <h3>状态码规范：</h3>
 * <ul>
 *     <li>200 - 成功</li>
 *     <li>400 - 请求参数错误</li>
 *     <li>401 - 未认证</li>
 *     <li>403 - 无权限</li>
 *     <li>404 - 资源不存在</li>
 *     <li>500 - 服务器内部错误</li>
 *     <li>1000+ - 业务状态码</li>
 * </ul>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Getter
public enum ResultCode {

    // ==================== 基础状态码 ====================

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 请求参数错误
     */
    BAD_REQUEST(400, "请求参数错误"),

    /**
     * 未认证（未登录）
     */
    UNAUTHORIZED(401, "未认证，请先登录"),

    /**
     * 无权限访问
     */
    FORBIDDEN(403, "无权限访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 请求方法不允许
     */
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    /**
     * 服务器内部错误
     */
    INTERNAL_ERROR(500, "服务器内部错误"),

    // ==================== 认证相关状态码 (1000-1099) ====================

    /**
     * 用户名或密码错误
     */
    LOGIN_FAILED(1001, "用户名或密码错误"),

    /**
     * 账号已被禁用
     */
    ACCOUNT_DISABLED(1002, "账号已被禁用"),

    /**
     * 账号已被锁定
     */
    ACCOUNT_LOCKED(1003, "账号已被锁定"),

    /**
     * Token 已过期
     */
    TOKEN_EXPIRED(1004, "Token 已过期，请重新登录"),

    /**
     * Token 无效
     */
    TOKEN_INVALID(1005, "Token 无效"),

    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(1006, "验证码错误"),

    // ==================== 用户相关状态码 (1100-1199) ====================

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(1100, "用户不存在"),

    /**
     * 用户名已存在
     */
    USER_EXISTS(1101, "用户名已存在"),

    /**
     * 邮箱已被使用
     */
    EMAIL_EXISTS(1102, "邮箱已被使用"),

    /**
     * 手机号已被使用
     */
    PHONE_EXISTS(1103, "手机号已被使用"),

    /**
     * 旧密码错误
     */
    OLD_PASSWORD_ERROR(1104, "旧密码错误"),

    /**
     * 两次密码不一致
     */
    PASSWORD_NOT_MATCH(1105, "两次密码不一致"),

    // ==================== 角色相关状态码 (1200-1299) ====================

    /**
     * 角色不存在
     */
    ROLE_NOT_FOUND(1200, "角色不存在"),

    /**
     * 角色编码已存在
     */
    ROLE_CODE_EXISTS(1201, "角色编码已存在"),

    /**
     * 不能删除系统角色
     */
    ROLE_SYSTEM_DELETE(1202, "不能删除系统内置角色"),

    // ==================== 菜单相关状态码 (1300-1399) ====================

    /**
     * 菜单不存在
     */
    MENU_NOT_FOUND(1300, "菜单不存在"),

    /**
     * 存在子菜单，不能删除
     */
    MENU_HAS_CHILDREN(1301, "存在子菜单，不能删除"),

    /**
     * 菜单已分配给角色，不能删除
     */
    MENU_ASSIGNED(1302, "菜单已分配给角色，不能删除"),

    // ==================== 文件相关状态码 (1400-1499) ====================

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAILED(1400, "文件上传失败"),

    /**
     * 文件类型不允许
     */
    FILE_TYPE_NOT_ALLOWED(1401, "文件类型不允许"),

    /**
     * 文件大小超出限制
     */
    FILE_SIZE_EXCEEDED(1402, "文件大小超出限制");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
