package com.energyfuture.encrypt.advice;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.energyfuture.encrypt.annotation.Encrypt;
import com.energyfuture.encrypt.bean.CommonResult;
import com.energyfuture.encrypt.enums.EncryptMethod;
import com.energyfuture.encrypt.utils.EncryptionAndDecryptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Objects;

/**
 * <p>
 * 加密处理
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 15:37
 */
@Slf4j
@RestControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<CommonResult<Object>> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public CommonResult<Object> beforeBodyWrite(CommonResult<Object> body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Method method = returnType.getMethod();
        if (Objects.isNull(method)){
            return body;
        }
        if (method.isAnnotationPresent(Encrypt.class)){
            Encrypt encrypt = method.getAnnotation(Encrypt.class);
            Object data = body.getData();
            if (Objects.isNull(data)) {
                return null;
            }
            body.setData(this.encryptResponseBody(encrypt.value(),data));
        }
        return body;
    }

    /**
     * 加密方法
     * @param method 加密类型
     * @param data 被加密数据
     * @return 加密后数据
     */
    private Object encryptResponseBody(EncryptMethod method, Object data){
        if (Objects.isNull(method)) {
            return data;
        }
        String s = null;
        try {
            s = ObjectUtil.isBasicType(data) ? Convert.toStr(data) : objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("返回对象转换异常",e);
            e.printStackTrace();
        }
        if (method == EncryptMethod.AES) {
            return EncryptionAndDecryptionUtil.AES.encryptHex(s);
        }
        return EncryptionAndDecryptionUtil.SM4.encryptHex(s);
    }
}
