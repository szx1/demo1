package com.example.demo.aop;

import cn.tongdun.tianzhou.encrypted.impl.sm4.SM4Util;
import com.example.demo.entity.Result;
import com.example.demo.util.JsonUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * @author zengxi.song
 * @date 2023/5/26
 */
@ControllerAdvice
public class EncryptRequestAdvice implements ResponseBodyAdvice<Result<Object>> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    @Override
    public Result<Object> beforeBodyWrite(Result<Object> body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            if (body.getData() != null) {
                System.out.println("接口加密前返回的数据：" + JsonUtil.jsonSerialize(body.getData()));
                String encryptStr = SM4Util.encrypt(JsonUtil.jsonSerialize(body.getData()), "C742B6116E3AF3488CD2C012EA85CD64");
                System.out.println("接口加密后返回的数据：" + encryptStr);
                body.setData(encryptStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
}
