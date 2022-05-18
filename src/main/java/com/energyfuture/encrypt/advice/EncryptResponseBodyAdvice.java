package com.energyfuture.encrypt.advice;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.energyfuture.encrypt.annotation.Encrypt;
import com.energyfuture.encrypt.dto.CommonResult;
import com.energyfuture.encrypt.enums.EncryptMethod;
import com.energyfuture.encrypt.utils.EncryptionAndDecryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<CommonResult<Object>> {

    @Autowired
    private EncryptionAndDecryptionUtil encryptionAndDecryptionUtil;

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
    private String encryptResponseBody(EncryptMethod method, Object data){
        if (Objects.isNull(method)) {
            return null;
        }
        String s = ObjectUtil.isBasicType(data) ? Convert.toStr(data) : JSONUtil.toJsonStr(data);
        if (method == EncryptMethod.AES) {
            return encryptionAndDecryptionUtil.aes.encryptHex(s);
        }
        return encryptionAndDecryptionUtil.sm4.encryptHex(s);
    }
}
