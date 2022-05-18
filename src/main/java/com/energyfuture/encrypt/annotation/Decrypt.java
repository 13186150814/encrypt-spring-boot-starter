package com.energyfuture.encrypt.annotation;

import com.energyfuture.encrypt.enums.EncryptMethod;

import java.lang.annotation.*;

/**
 * <p>
 * <p>解密注解</p>
 * <p>只正对于加了@RequestBody对象参数生效</p>
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 15:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Documented
public @interface Decrypt {

    /**
     * 解密方式默认sm4
     */
    EncryptMethod value() default EncryptMethod.SM4;
}
