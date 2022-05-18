package com.energyfuture.encrypt.utils;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.energyfuture.encrypt.properties.EncryptProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

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
@EnableConfigurationProperties(EncryptProperties.class)
public class EncryptionAndDecryptionUtil {

    @Autowired
    private EncryptProperties encryptProperties;

    /**
     * 构建aes加密对象
     */
    public final SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, encryptProperties.getKey().getBytes(StandardCharsets.UTF_8));
    /**
     * 构建sm4加密对象
     */
    public final SymmetricCrypto sm4 = SmUtil.sm4(encryptProperties.getKey().getBytes(StandardCharsets.UTF_8));
}
