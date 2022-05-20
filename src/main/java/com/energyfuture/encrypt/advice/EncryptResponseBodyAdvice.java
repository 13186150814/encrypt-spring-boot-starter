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
 * 响应body加密处理；</br>
 * 此处直接明确加密的是自定义的全局返回 CommonResult 对象，是因为一般web项目后台全局返回对象基本都是 code、msg、data三个对象，其中 code 和 msg 不涉及到涉密没必要加密，只要加密data就好。
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

    /**
     * supports：该方法用来判断哪些接口需要处理接口加密，这里的判断逻辑是方法上含有 @Encrypt 注解的接口，就执行beforeBodyWrite方法。
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    /**
     * 读取返回的全景统一返回对象（ CommonResult ）并加密data数据，
     */
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
            // 检查data是否是基本数据类型，不是基本类型就转json
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
