package com.example.demo.controller;

import cn.tongdun.tianzhou.common.base.BusException;
import cn.tongdun.tianzhou.common.base.Response;
import cn.tongdun.tianzhou.encrypted.impl.anno.Encrypt;
import cn.tongdun.tianzhou.encrypted.impl.anno.JsonDecrypt;
import cn.tongdun.tianzhou.encrypted.impl.anno.UrlEncodedDecrypt;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengxi.song
 * @date 2023/5/26
 */
@RestController
@RequestMapping("encrypt")
public class EncryptController {

    @PostMapping("json")
    @JsonDecrypt
    @Encrypt
    public Response<User> test(@RequestBody Map<String, String> params, HttpServletResponse httpServletResponse) {
        if (params.size() == 2) {
            throw new BusException("test error,map size 2");
        }
        return Response.success(new User("szx", 23, new ArrayList<>()));
    }

    @PostMapping("param")
    @UrlEncodedDecrypt
    @Encrypt
    public Response<User> test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String name, String age) {
        Map<String, String> params = parseHttpServletRequest(httpServletRequest);
        return Response.success(new User("szx", 23, new ArrayList<>()));
    }

    public static Map<String, String> parseHttpServletRequest(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, String> result = new HashMap<>(map.size());
        map.forEach((key, valueArray) -> {
            String value = valueArray[0].trim();
            if (!isNullValue(value)) {
                result.put(key, value);
            }
        });
        return result;
    }

    public static boolean isNullValue(String value) {
        return (value == null || "".equals(value) || "null".equalsIgnoreCase(value));
    }
}
