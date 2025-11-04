package com.userlabel.core.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通用响应对象
 */
@Data
@Accessors(chain = true)
public class Result<T> {
    
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
     * 成功
     */
    public static <T> Result<T> success() {
        return new Result<T>()
                .setCode(200)
                .setMessage("success");
    }

    /**
     * 成功，带数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(200)
                .setMessage("success")
                .setData(data);
    }

    /**
     * 失败
     */
    public static <T> Result<T> error(String message) {
        return new Result<T>()
                .setCode(500)
                .setMessage(message);
    }

    /**
     * 失败，带状态码
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message);
    }
}