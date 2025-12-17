package com.myadmin.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果封装类
 * <p>
 * 规范化前后端数据交互格式，包含：
 * <ul>
 *     <li>code - 状态码</li>
 *     <li>message - 消息</li>
 *     <li>data - 数据</li>
 *     <li>timestamp - 时间戳</li>
 * </ul>
 * </p>
 *
 * @param <T> 数据类型
 * @author myadmin
 * @since 1.0.0
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 私有构造函数
     */
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return Result 对象
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 成功响应（带数据）
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return Result 对象
     */
    public static <T> Result<T> success(T data) {
        return success("操作成功", data);
    }

    /**
     * 成功响应（带消息和数据）
     *
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return Result 对象
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 失败响应（带消息）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result 对象
     */
    public static <T> Result<T> fail(String message) {
        return fail(ResultCode.INTERNAL_ERROR.getCode(), message);
    }

    /**
     * 失败响应（带状态码和消息）
     *
     * @param code    状态码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result 对象
     */
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    /**
     * 失败响应（使用 ResultCode）
     *
     * @param resultCode 状态码枚举
     * @param <T>        数据类型
     * @return Result 对象
     */
    public static <T> Result<T> fail(ResultCode resultCode) {
        return fail(resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return ResultCode.SUCCESS.getCode().equals(this.code);
    }
}
