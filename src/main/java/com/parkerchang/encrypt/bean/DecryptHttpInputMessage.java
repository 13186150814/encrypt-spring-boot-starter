package com.parkerchang.encrypt.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.InputStream;

/**
 * <p>
 * 解密消息流
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/19 10:34
 */
@NoArgsConstructor
@AllArgsConstructor
public class DecryptHttpInputMessage implements HttpInputMessage {

    private InputStream body;

    private HttpHeaders headers;

    @Override
    public InputStream getBody() {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
