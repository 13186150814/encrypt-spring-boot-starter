package com.parkerchang.encrypt.properties;

import cn.hutool.core.util.RandomUtil;
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
     * 默认加密秘钥
     */
    private String key = RandomUtil.randomString(16);


}
