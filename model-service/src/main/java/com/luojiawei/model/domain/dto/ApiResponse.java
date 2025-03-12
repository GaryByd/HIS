package com.luojiawei.model.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应格式（泛型增强版）
 * @param <T> 响应数据的类型
 */
@Data
@NoArgsConstructor
public class ApiResponse<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    // ========== 静态工厂方法 ========== //

    /**
     * 成功响应（无数据）
     */
    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>()
                .setSuccess(true)
                .setCode(200)
                .setMessage("操作成功")
                .setData(data);
    }

    /**
     * 成功响应（自定义消息）
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>()
                .setSuccess(true)
                .setCode(200)
                .setMessage(message)
                .setData(data);
    }

    /**
     * 错误响应（默认400错误）
     */
    public static <T> ApiResponse<T> error(String message) {
        return error(400, message);
    }

    /**
     * 错误响应（自定义状态码）
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return error(code, message, null);
    }

    /**
     * 错误响应（带错误详情数据）
     */
    public static <T> ApiResponse<T> error(int code, String message, T errorData) {
        return new ApiResponse<T>()
                .setSuccess(false)
                .setCode(code)
                .setMessage(message)
                .setData(errorData);
    }

    // ========== 链式调用方法 ========== //

    public ApiResponse<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public ApiResponse<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public ApiResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse<T> setData(T data) {
        this.data = data;
        return this;
    }
}
