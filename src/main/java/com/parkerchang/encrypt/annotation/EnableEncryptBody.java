package com.parkerchang.encrypt.annotation;

import com.parkerchang.encrypt.advice.DecryptRequestBodyAdvice;
import com.parkerchang.encrypt.advice.EncryptResponseBodyAdvice;
import com.parkerchang.encrypt.properties.EncryptProperties;
import com.parkerchang.encrypt.utils.EncryptionAndDecryptionUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 加解密Body功能开启类
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
