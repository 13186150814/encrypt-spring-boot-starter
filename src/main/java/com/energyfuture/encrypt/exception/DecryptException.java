package com.energyfuture.encrypt.exception;

/**
 * <p>
 * 自定义解密异常
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 17:17
 */
public class DecryptException extends RuntimeException{

    public DecryptException(String message) {
        super(message);
    }

    public DecryptException(){
        super("Decrypting data failed. (解密数据失败)");
    }
}
