package com.energyfuture.encrypt.utils;

import cn.hutool.core.convert.Convert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * ServletUtils
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/20 14:10
 */
public class ServletUtils {


    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * <p>获取response</p>
     *
     * @return javax.servlet.http.HttpServletResponse
     * @author Parker
     * @date 2022-02-24 14:14
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * <p>获取request</p>
     *
     * @return javax.servlet.http.HttpServletRequest
     * @author Parker
     * @date 2022-02-24 14:15
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * <p>获取Integer参数</p>
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return java.lang.Integer
     * @author Parker
     * @date 2022-02-24 14:16
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     * <p>获取Integer参数</p>
     *
     * @param name 名称
     * @return java.lang.Integer
     * @author Parker
     * @date 2022-02-24 14:17
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * <p>获取String参数</p>
     *
     * @param name 名称
     * @return java.lang.String
     * @author Parker
     * @date 2022-02-24 14:17
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * <p>获取String参数</p>
     *
     * @param name         名称
     * @param defaultValue 默认值
     * @return java.lang.String
     * @author Parker
     * @date 2022-02-24 14:18
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }
}
