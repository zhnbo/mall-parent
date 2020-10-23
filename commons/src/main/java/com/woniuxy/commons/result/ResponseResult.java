package com.woniuxy.commons.result;

/**
 * 响应结果集
 * @author zh_o
 * @date: 2020-10-06
 */
public class ResponseResult<T> {

    /**
     * 业务状态码
     */
    private Integer code;

    /**
     * 业务描述信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 构造器
     * @param resultStatus
     * @param data
     */
    private ResponseResult(ResultStatusEnum resultStatus,T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    /**
     * 自定义message
     * @param msg
     * @param data
     */
    private ResponseResult(String msg, T data) {
        this.code = 400;
        this.message = msg;
        this.data = data;
    }

    /**
     * 业务成功，无返回数据
     * @return
     */
    public static ResponseResult<Void> success() {
        return new ResponseResult<Void>(ResultStatusEnum.SUCCESS,null);
    }

    /**
     * 业务成功，有返回数据
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(ResultStatusEnum.SUCCESS,data);
    }

    /**
     * 业务成功，自定义业务状态码
     * @param resultStatus
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(ResultStatusEnum resultStatus, T data) {
        if (resultStatus == null) {
            return success(data);
        }
        return new ResponseResult<T>(resultStatus, data);
    }

    /**
     * 业务异常，无返回数据
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> error() {
        return new ResponseResult<T>(ResultStatusEnum.ERROR, null);
    }

    /**
     * 业务异常，有返回数据
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> error(T data) {
        return new ResponseResult<T>(ResultStatusEnum.ERROR,data);
    }

    /**
     * 业务异常，自定义业务状态码
     * @param resultStatus
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> error(ResultStatusEnum resultStatus, T data) {
        if (resultStatus == null) {
            return error(data);
        }
        return new ResponseResult<T>(resultStatus, data);
    }

    /**
     * 自定义状态提示
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> error(String msg) {
        return new ResponseResult(msg, null);
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
