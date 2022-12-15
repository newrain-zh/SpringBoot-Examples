package com.example.common;


import lombok.Data;

/**
 * @author zhiqin.zhang
 */
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private boolean success = true;
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, T data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> error() {
        return error(ApiCodeMsg.ERROR.getCode(), "未知异常，请联系管理员");
    }

    public static <T> Result<T> error(String message) {
        return error(ApiCodeMsg.ERROR.getCode(), message);
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>(code, message);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(Integer code, T data, String message) {
        Result<T> result = new Result<>(code, data, message);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(ApiCodeMsg msg) {
        return error(msg.getCode(), msg.getDesc());
    }

    public static <T> Result<T> success() {
        return success(null, "处理成功");
    }

    public static <T> Result<T> success(T data) {
        return success(data, "处理成功");
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(ApiCodeMsg.SUCCESS.getCode(), data, message);
    }

    /**
     * 这里针对根据布尔值返回不同的处理成功和失败的响应，做统一处理。
     *
     * @param bol api响应结果 bol代表处理成功 false 失败
     * @return 统一返回的结果
     */
    public static Result<Boolean> response(Boolean bol) {
        if (bol) {
            return Result.success();
        }
        return Result.error(ApiCodeMsg.ERROR);
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result<T> data(T data) {
        return success(data, null);
    }
}
