package com.parkerchang.encrypt.annotation;

import com.parkerchang.encrypt.enums.EncryptMethod;

import java.lang.annotation.*;

/**
 * <p>
 * 加密
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 15:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Encrypt {
    /**
     * 加密方式默认sm4
     */
    EncryptMethod value() default EncryptMethod.SM4;


}
