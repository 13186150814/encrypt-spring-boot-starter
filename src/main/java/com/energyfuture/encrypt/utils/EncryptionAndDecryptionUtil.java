package com.energyfuture.encrypt.utils;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.energyfuture.encrypt.properties.EncryptProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 加解密工具
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 17:26
 */
@Component
public class EncryptionAndDecryptionUtil {

    @Autowired
    private EncryptProperties encryptProperties;

    /**
     * aes加密对象
     */
    public static SymmetricCrypto AES;
    /**
     * sm4加密对象
     */
    public static SymmetricCrypto SM4;


    /**
     * 组装静态变量
     */
    @PostConstruct
    public void init() {
        byte[] key = encryptProperties.getKey().getBytes(StandardCharsets.UTF_8);
        AES = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        SM4 = SmUtil.sm4(key);
    }
}
