package com.energyfuture.encrypt.advice;

import cn.hutool.core.io.IoUtil;
import com.energyfuture.encrypt.annotation.Decrypt;
import com.energyfuture.encrypt.enums.EncryptMethod;
import com.energyfuture.encrypt.exception.DecryptException;
import com.energyfuture.encrypt.utils.EncryptionAndDecryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * <p>
 * 解密body参数
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 16:42
 */
@ControllerAdvice
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Autowired
    private EncryptionAndDecryptionUtil encryptionAndDecryptionUtil;

    /**
     * supports：该方法用来判断哪些接口需要处理接口解密，这里的判断逻辑是方法上或者参数上含有 @Decrypt 注解的接口，处理解密问题。
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(Decrypt.class) || methodParameter.hasParameterAnnotation(Decrypt.class);
    }

    /**
     * beforeBodyRead：这个方法会在参数转换成具体的对象之前执行，我先从流中加载到数据，然后对数据进行解密，解密完成后再重新构造 HttpInputMessage 对象返回。
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        byte[] bytes = IoUtil.readBytes(inputMessage.getBody());
        if (Objects.isNull(bytes)) {
            return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
        }
        Decrypt decrypt = null;
        // 尝试从方法上面取解密注解
        if (parameter.hasMethodAnnotation(Decrypt.class)) {
            decrypt = parameter.getMethodAnnotation(Decrypt.class);
        }
        // 尝试从参数上面取解密注解
        if (parameter.hasParameterAnnotation(Decrypt.class)){
            decrypt = parameter.getParameterAnnotation(Decrypt.class);
        }
        if (Objects.isNull(decrypt)) {
            throw new DecryptException("获取解密方式异常");
        }
        // 解密
        byte[] decryptBytes = this.decryptRequestBody(decrypt.value(), bytes);
        final ByteArrayInputStream bais = new ByteArrayInputStream(decryptBytes);
        return new HttpInputMessage() {
            @Override
            public InputStream getBody() {
                return bais;
            }

            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }
        };
    }

    /**
      * <p>解密参数</p>
      * @author Parker
      * @date 2022-05-18 17:33
      */
    private byte[] decryptRequestBody(EncryptMethod method,byte[] bytes){
        if (Objects.isNull(method)){
            return bytes;
        }
        if (method == EncryptMethod.AES) {
            return encryptionAndDecryptionUtil.aes.decrypt(bytes);
        }
        return encryptionAndDecryptionUtil.sm4.decrypt(bytes);
    }
}
