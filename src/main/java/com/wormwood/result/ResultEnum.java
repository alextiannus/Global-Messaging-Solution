/**
 *
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.wormwood.result;

import com.wormwood.config.i18n.MessageSourceService;
import org.springframework.http.HttpStatus;

/**
 * 定义返回结果
 */
public enum ResultEnum {
                        /**
                         * 定义规则：
                         * 2000-2999是操作成功定义的CODE范围
                         * 4000-4999是权限相关CODE范围
                         * 5000-5999是系统异常CODE范围
                         * 6000-6999是业务失败相关CODE范围
                         */
                        SUCCESS(HttpStatus.OK, true, 2000, "成功"),
                        QUERY_SUCCESS(HttpStatus.OK, true, 2001, "查询成功"),
                        UPDATE_SUCCESS(HttpStatus.OK, true, 2002, "更新成功"),
                        DELETE_SUCCESS(HttpStatus.OK, true, 2003, "删除成功"),
                        INSERT_SUCCESS(HttpStatus.OK, true, 2004, "创建成功"),
                        BATCH_INSERT(HttpStatus.OK, true, 2005, "批量创建完成"),
                        QUERY_RESULT_IS_NULL(HttpStatus.OK, true, 2006, "查询成功，但查询结果为空"),
                        UPLOAD_SUCCESS(HttpStatus.OK, true, 2007, "上传成功"),
                        UNAUTHORIZED(HttpStatus.OK, false, 4001, "未授权,请重新登录"),
                        TOKEN_EXPIRED(HttpStatus.OK, false, 4002, "登陆过期"),
                        ILLEGAL_TOKEN(HttpStatus.OK, false, 4003, "非法token"),
                        BAD_NAME_OR_PASSWORD(HttpStatus.OK, false, 4004, "用户名或密码错误"),
                        ILLEGAL_PHONE(HttpStatus.OK, false, 4005, "电话号码错误"),
                        ILLEGAL_AUTH_CODE(HttpStatus.OK, false, 4006, "验证码错误"),
                        ACCESS_DENIED(HttpStatus.OK, false, 4007, "没有权限访问该资源"),
                        OPERATE_DENIED(HttpStatus.OK, false, 4008, "没有权限操作该资源"),
                        USER_NOT_EXIST(HttpStatus.OK, false, 4009, "用户不存在"),
                        IP_INVALID(HttpStatus.OK, false, 4010, "IP验证失败"),
                        USER_DISABLED(HttpStatus.OK, false, 4011, "用户已被禁用"),
                        UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, false, 5000, "未知异常"),
                        NOT_FOUND(HttpStatus.NOT_FOUND, false, 5001, "未找到对应调用"),
                        METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, false, 5002, "不支持的方法调用"),
                        UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, false, 5003, "不支持的HTTP MEDIA类型"),
                        RATE_LIMIT_EXCEEDED(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, false, 5004, "调用太频繁，超过限制"),
    FAIL(HttpStatus.OK, false, 6000, "失败"),
    PARAMS_NOT_VALID(HttpStatus.OK, false, 6009, "请求参数错误");

    /** 成功标识 */
    private boolean    success;

    /** 结果码 */
    private int        code;

    /** 返回信息 */
    private String     message;

    /** http Status */
    private HttpStatus httpStatus;

    /**
     * Getter method for property <tt>httpStatus</tt>.
     *
     * @return property value of httpStatus
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    /**
     * @param success
     * @param code
     * @param message
     */
    private ResultEnum(HttpStatus httpStatus, boolean success, int code, String message) {
        this.httpStatus = httpStatus;
        this.success = success;
        this.code = code;
        this.message = message;
    }

    /**
     * Getter method for property <tt>success</tt>.
     *
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public int getCode() {
        return code;
    }


    /**
     * 根据CODE返回枚举
     * @param code
     * @return
     */
    public static ResultEnum getResultEnumByCode(int code) {
        for (ResultEnum resultEnum : ResultEnum.values()) {
            if (resultEnum.getCode() == code) {
                return resultEnum;
            }
        }
        return null;
    }

    private static MessageSourceService messageSourceService;

    public static void setMessageSourceService(MessageSourceService mss){
        messageSourceService = mss;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     *
     * @return property value of msg
     */
    public String getMessage() {
        return messageSourceService == null ? message : messageSourceService.getMessage(String.valueOf(code),message);
    }

}
