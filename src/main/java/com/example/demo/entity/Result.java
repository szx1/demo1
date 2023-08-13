package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

/**
 * Standard http return type wrapper, provided that the bean of {@link MessageSource} must be configured.
 *
 * @author liutianlu
 * <br/>Created 2021/9/21 1:31 PM
 */
@Setter
@Getter
public class Result<T> {
    private static final Logger LOG = LoggerFactory.getLogger(Result.class);
    /**
     * 返回结果状态码
     */
    private Integer code;
    /**
     * 返回结果标识
     */
    private Boolean success;
    /**
     * 附加信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> response = new Result<T>();
        response.success = true;
        response.data = data;
        response.code = 200;
        response.message = "操作成功";
        return response;
    }

}
