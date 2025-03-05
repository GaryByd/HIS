package com.luojiawei.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    private Long total;

    // 简单的成功操作返回
    public static <T> Result<T> ok() {
        return new Result<>(200, "Success", null, null);
    }
    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "Success", data, null);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(200, msg, data, null);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return new Result<>(200, msg, data, null);
    }

    // 登录错误
    public static <T> Result<T> fail(String errorMsg) {
        return new Result<>(500, errorMsg, null, null);
    }

    // 其他错误
    public static <T> Result<T> fail(Integer code, String errorMsg) {
        return new Result<>(code, errorMsg, null, null);
    }
}
