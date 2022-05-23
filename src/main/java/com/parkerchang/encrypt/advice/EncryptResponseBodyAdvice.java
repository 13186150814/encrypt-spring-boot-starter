package com.parkerchang.encrypt.advice;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.parkerchang.encrypt.annotation.Encrypt;
import com.parkerchang.encrypt.enums.EncryptMethod;
import com.parkerchang.encrypt.utils.EncryptionAndDecryptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 响应body加密处理；</br>
 * 一般web项目后台全局返回对象基本都是 code、msg、data三个对象，其中 code 和 msg 不涉及到涉密没必要加密，只要加密data就好。
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 15:37
 */
@Slf4j
@RestControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * supports：该方法用来判断哪些接口需要处理接口加密，这里的判断逻辑是方法上含有 @Encrypt 注解的接口，就执行beforeBodyWrite方法。
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    /**
     * 读取返回对象并加密data数据，如果没有data属性就加密返回对象自身。
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Method method = returnType.getMethod();
        if (Objects.isNull(method)){
            return body;
        }
        if (method.isAnnotationPresent(Encrypt.class)){
            Encrypt encrypt = method.getAnnotation(Encrypt.class);
            if (Objects.isNull(body)) {
                return null;
            }
            // 如果接口方法返回的是基本类型数据,就直接把返回值加密
            if (ObjectUtil.isBasicType(body)) {
                return this.encryptResponseBody(encrypt.value(),body);
            }
            // 如果返回对象是数组或者集合就直接把返回值加密
            if (ArrayUtil.isArray(body) || body instanceof Collection){
                return this.encryptResponseBody(encrypt.value(),body);
            }
            // 如果不是基本类型,也不是数组或集合，就转MAP对象
            Map<String, Object> map = objectMapper.readValue(this.writeValueAsString(body), new TypeReference<Map<String, Object>>() {});
            // 如果返回的对象中第一级有data字段就只加密data
            Object data = map.get("data");
            if (Objects.isNull(data)){
                return this.encryptResponseBody(encrypt.value(),body);
            }
            // 加密后的data
            Object encryptData = this.encryptResponseBody(encrypt.value(), data);
            map.put("data",encryptData);
            return map;

        }
        return body;
    }

    /**
     * 加密方法
     * @param encryptMethod 加密类型
     * @param data 被加密数据
     * @return 加密后数据
     */
    private Object encryptResponseBody(EncryptMethod encryptMethod, Object data){
        if (Objects.isNull(encryptMethod)) {
            return data;
        }
        // 检查data是否是基本数据类型，不是基本类型就转json
        String s = ObjectUtil.isBasicType(data) ? Convert.toStr(data) : this.writeValueAsString(data);
        if (encryptMethod == EncryptMethod.AES) {
            return EncryptionAndDecryptionUtil.AES.encryptHex(s);
        }
        return EncryptionAndDecryptionUtil.SM4.encryptHex(s);
    }
    /**
      * <p>转换json字符串</p>
      * @author Parker
      * @date 2022-05-23 10:11
      */
    private String writeValueAsString(Object obj){
        if (Objects.isNull(obj)) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("返回对象:{} 转换异常",obj,e);
            e.printStackTrace();
        }
        return null;
    }
}
