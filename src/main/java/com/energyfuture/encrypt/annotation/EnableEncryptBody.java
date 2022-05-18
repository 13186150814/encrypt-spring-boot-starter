package com.energyfuture.encrypt.annotation;

import com.energyfuture.encrypt.advice.DecryptRequestBodyAdvice;
import com.energyfuture.encrypt.advice.EncryptResponseBodyAdvice;
import com.energyfuture.encrypt.properties.EncryptProperties;
import com.energyfuture.encrypt.utils.EncryptionAndDecryptionUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 加解密功能开启类
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 17:43
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EncryptProperties.class,
        EncryptionAndDecryptionUtil.class,
        EncryptResponseBodyAdvice.class,
        DecryptRequestBodyAdvice.class,})
public @interface EnableEncryptBody {
}
