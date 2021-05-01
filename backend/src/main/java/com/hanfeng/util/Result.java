package com.hanfeng.util;

/**
 * 响应给前端的实体类型
 *
 * @author HanFeng
 */
public class Result<T> {

    private int code = 1;

    private String message = "操作成功";

    private T body;


    public int getCode() {
        return code;
    }

    /**
     * 错误的响应
     *
     * @param message message
     * @param body    body
     * @param <T>     泛型类型
     * @return 封装的实体类型
     */
    public static <T> Result<T> error(String message, T body) {
        Result<T> result = new Result<>();
        result.code = 0;
        result.message = message;
        result.body = body;
        return result;
    }

    /**
     * 正确的相应
     *
     * @param message message
     * @param body    body
     * @param <T>     泛型类型
     * @return 封装的实体类型
     */
    public static <T> Result<T> success(String message, T body) {
        Result<T> result = new Result<>();
        result.body = body;
        result.message = message;
        return result;
    }

    /**
     * 正确的相应
     *
     * @param body body
     * @param <T>  泛型类型
     * @return 封装的实体类型
     */
    public static <T> Result<T> success(T body) {
        Result<T> result = new Result<>();
        result.body = body;
        return result;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
