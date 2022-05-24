package com.parkerchang.encrypt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 加密配置
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 15:28
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {

    /**
     * 秘钥 必须是一串32个字符的16进制字符串（使用16位随机字符串转byte数组会和前端秘钥对不上）;</br>
     * 不设置默认随机生成
     */
    private String key;


}
