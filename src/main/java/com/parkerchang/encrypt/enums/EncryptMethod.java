package com.parkerchang.encrypt.enums;

/**
 * <p>
 * 加密方式
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 15:21
 */
public enum EncryptMethod {
    /**
     * 对称加密 AES (默认AES/ECB/PKCS5Padding)
     */
    AES,

    /**
     * 国密算法 SM4
     */
    SM4

}
