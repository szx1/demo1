package com.example.demo.aop;

import cn.tongdun.tianzhou.common.base.BusException;
import cn.tongdun.tianzhou.common.base.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zengxi.song
 * @date 2023/5/29
 */
@RestControllerAdvice("com.example.demo.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(BusException.class)
    public Response<Object> dealEx(Exception e) {
        return Response.error(e.getMessage());
    }
}
