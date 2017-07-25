/**
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.wormwood.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wormwood.result.ResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Collection;

/**
 * 基类Response
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 6584856368834688158L;
    protected boolean success;
    /**
     * 状态码
     */
    protected int code;
    /**
     * 消息 ：可为空，当请求异常时message将不为空。
     */
    protected String msg;

    protected String version = "v1";
    /**
     * 请求id
     */
    protected String uid;
    /**
     * 返回数据
     */
    protected T data;
    /**
     * Http状态码
     */
    @JsonIgnore
    protected HttpStatus httpStatus;


    public final static Response SUCCESS = new Response(ResultEnum.SUCCESS);
    public final static Response FAIL = new Response(ResultEnum.FAIL);

    /**
     * 如果有数据
     * 并且ResultEnum.isSuccess = true
     * 该方法返回true
     *
     * @return
     */
    @JsonIgnore
    public boolean isSuccessWithData() {
        ResultEnum resultEnum = getResult();
        if (null != resultEnum && resultEnum.isSuccess() && null != data
                && !(data instanceof Collection && ((Collection) data).isEmpty())) {
            return true;
        }
        return false;
    }

    @JsonIgnore
    public boolean isSuccessOnlyFlag() {
        ResultEnum resultEnum = getResult();
        if (null != resultEnum && resultEnum.isSuccess()) {
            return true;
        }
        return false;
    }

    /**
     * 根据 ResultEnum 创建 ResponseEntity
     *
     * @return
     */
    public ResponseEntity<Response<T>> build() {
        return ResponseEntity.status(httpStatus).body(this);
    }

    /**
     * 返回枚举结果
     *
     * @return
     */
    @JsonIgnore
    public ResultEnum getResult() {
        return ResultEnum.getResultEnumByCode(this.code);
    }

    public void setResult(ResultEnum result) {
        this.code = result.getCode();
        this.msg = result.getMessage();
        this.httpStatus = result.getHttpStatus();
        this.success = result.isSuccess();
    }

    public Response<T> withResult(ResultEnum result) {
        this.setResult(result);
        return this;
    }

    public Response() {
        super();
        setDefaultResult();
    }

    private void setDefaultResult() {
        this.code = ResultEnum.QUERY_SUCCESS.getCode();
        this.msg = ResultEnum.QUERY_SUCCESS.getMessage();
        this.httpStatus = ResultEnum.QUERY_SUCCESS.getHttpStatus();
        this.success = ResultEnum.QUERY_SUCCESS.isSuccess();
    }

    /**
     * 根据结果枚举和数据 构造Response
     *
     * @param data
     * @param resultEnum
     */
    public Response(T data, ResultEnum resultEnum) {
        super();
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMessage();
        this.data = data;
        this.httpStatus = resultEnum.getHttpStatus();
        this.success = resultEnum.isSuccess();
    }

    /**
     * 根据结果枚举构造Response
     *
     * @param resultEnum
     */
    public Response(ResultEnum resultEnum) {
        super();
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMessage();
        this.httpStatus = resultEnum.getHttpStatus();
        this.success = resultEnum.isSuccess();
    }

    /**
     * 根据结果枚举和自定义信息构造Response
     *
     * @param resultEnum
     * @param msg        自定义消息    如: 非法参数 { phone为空 }
     */
    public Response(ResultEnum resultEnum, String msg) {
        super();
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMessage() + " { " + msg + " }";
        this.httpStatus = resultEnum.getHttpStatus();
        this.success = resultEnum.isSuccess();
    }

    /**
     * 默认result为查询成功
     *
     * @param data
     */
    public Response(T data) {
        super();
        this.data = data;
        setDefaultResult();
    }

    /**
     * @param code
     * @param msg
     */
    public Response(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param httpStatus
     * @param msg
     */
    public Response(HttpStatus httpStatus, String msg) {
        super();
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.msg = msg;
    }

    /**
     * @param code
     * @param msg
     * @param data
     */
    public Response(int code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(int code, String msg, T data, boolean success) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public Response<T> withCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     *
     * @return property value of msg
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Setter method for property <tt>msg</tt>.
     *
     * @param message value to be assigned to property msg
     */
    public Response<T> withMessage(String message) {
        this.msg = message;
        return this;
    }

    /**
     * Getter method for property <tt>data</tt>.
     *
     * @return property value of data
     */
    public T getData() {
        return data;
    }

    /**
     * Setter method for property <tt>data</tt>.
     *
     * @param data value to be assigned to property data
     */
    public Response<T> withData(T data) {
        this.data = data;
        return this;
    }

    @JsonIgnore
    public T getValue() {
        return getData();
    }

    public void setValue(T data) {
        this.data = data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
