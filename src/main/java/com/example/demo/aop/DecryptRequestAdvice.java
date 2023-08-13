package com.example.demo.aop;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import cn.tongdun.tianzhou.encrypted.impl.sm4.SM4Util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author zengxi.song
 * @date 2023/5/26
 */
@ControllerAdvice
public class DecryptRequestAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage,
                                           MethodParameter parameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        InputStream inputStream = inputMessage.getBody();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String requestBody = new String(bytes);

        try {

            String decryptData = SM4Util.decrypt(requestBody, "C742B6116E3AF3488CD2C012EA85CD64");
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptData.getBytes());
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return byteArrayInputStream;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }

    public static void main(String[] args) {
        String key = "C742B6116E3AF3488CD2C012EA85CD64";
        String s = "{\n" +
                "    \"name\":\"szx\",\n" +
                "    \"age\":23\n" +
                "}";
        System.out.println(SM4Util.encrypt(s, key));
        System.out.println(SM4Util.encrypt("szx,1243,8", key));
        System.out.println(SM4Util.encrypt("23", key));
        System.out.println(SM4Util.encrypt("16852638944990EB2002FRY2KJNKHCWKV2J", key));

        System.out.println(SM4Util.decrypt("EosEoIn0GUrlgDR37903rn7fd659frO570SV/jo4WtsHnt0uunCIhf5hCdyAMQPqly4VRmczOIZc16RotShgmQ==", key));
    }
}
