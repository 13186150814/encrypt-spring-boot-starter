package com.energyfuture.encrypt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {

    /**
     * 默认加密秘钥
     */
    private String key = "2ff02f33-5b4c-4629-b6dc-4782de280933";


}
