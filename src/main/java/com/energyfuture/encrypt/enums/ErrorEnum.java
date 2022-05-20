package com.energyfuture.encrypt.enums;

import lombok.Getter;

/**
  * <p>错误枚举</p>
  * @author Parker
  * @date 2022-05-20 13:52
  */
@Getter
public enum ErrorEnum {
    /**
     * 成功
     */
    SECUESS(200,"成功"),

    ERROR(500,"系统异常，请稍后重试"),

    UNAUTHORIZED(401, "token无效"),

    FORBIDDEN(403, "没有权限访问该资源"),

    NO_HANDLER_FOUND(404, "该资源不存在"),

    ILLEGAL_ARGUMENT_EXCEPTION(501,"非法参数"),

    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(502,"参数错误"),
    HANDLE_HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(503, "不接受的媒体类型"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(504, "不支持的请求方法"),
    SERVER_ANOMALY(506,"服务异常"),
    NULL_POINTER(507,"空对象异常"),
    BAD_REQUEST(508,"错误的请求"),
    INTERNAL_SERVER_ERROR(509,"服务器内部错误");


    /**
     * 错误编码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String message;

    ErrorEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
}
