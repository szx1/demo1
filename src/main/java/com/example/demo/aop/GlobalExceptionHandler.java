package com.example.demo.aop;

import cn.tongdun.tianzhou.common.base.BusException;
import cn.tongdun.tianzhou.common.base.JsonUtil;
import cn.tongdun.tianzhou.common.base.Response;
import com.example.demo.entity.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<Object> validationException(Exception e) {
        BindingResult bindResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException) {
            bindResult = ((BindException) e).getBindingResult();
        }
        Map<String, String> errorMap = new HashMap<>(16);
        if (bindResult != null) {
            bindResult.getFieldErrors().forEach((fieldError) ->
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return Result.error("error" + errorMap);
    }

    /**
     * 参数验证异常处理
     *
     * @param ex
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Object> argumentValidationHandler(ConstraintViolationException ex) {
        String errMessage = ex.getConstraintViolations()
                .stream()
                .map(constraintViolation -> constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage())
                .collect(Collectors.joining("!"));
        return Result.error(errMessage);
    }

    public static void main(String[] args) {
        String s="{\n" +
                "    \"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf14\",\n" +
                "    \"flowNodeDefinitions\":[\n" +
                "        {\n" +
                "            \"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf140\",\n" +
                "            \"name\":\"开始\",\n" +
                "            \"attributes\":{\n" +
                "\n" +
                "            },\n" +
                "            \"x\":200,\n" +
                "            \"y\":200,\n" +
                "            \"nodeType\":\"start\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf141\",\n" +
                "            \"name\":\"IV黑样本同步\",\n" +
                "            \"attributes\":{\n" +
                "\n" +
                "            },\n" +
                "            \"x\":400,\n" +
                "            \"y\":200,\n" +
                "            \"nodeType\":\"iv_black_sample_sync\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf142\",\n" +
                "            \"name\":\"指标评估\",\n" +
                "            \"attributes\":{\n" +
                "\n" +
                "            },\n" +
                "            \"x\":600,\n" +
                "            \"y\":200,\n" +
                "            \"nodeType\":\"metric_evaluate\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf143\",\n" +
                "            \"name\":\"评估结果同步\",\n" +
                "            \"attributes\":{\n" +
                "\n" +
                "            },\n" +
                "            \"x\":800,\n" +
                "            \"y\":200,\n" +
                "            \"nodeType\":\"evaluate_result_sync\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf144\",\n" +
                "            \"name\":\"结束\",\n" +
                "            \"attributes\":{\n" +
                "\n" +
                "            },\n" +
                "            \"x\":1000,\n" +
                "            \"y\":200,\n" +
                "            \"nodeType\":\"end\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"flowLineDefinitions\":[\n" +
                "        {\n" +
                "            \"id\":\"63c0bc08b10e4a1f8b53cc4684b77925\",\n" +
                "            \"attributes\":{\n" +
                "\n" +
                "            },\n" +
                "            \"fromPoint\":\"1\",\n" +
                "            \"toPoint\":\"3\",\n" +
                "            \"sourceNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf140\",\n" +
                "            \"targetNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf141\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"14698a4749394dca972cc8b4b55737e1\",\n" +
                "            \"attributes\":{\n" +
                "\n" +
                "            },\n" +
                "            \"fromPoint\":\"1\",\n" +
                "            \"toPoint\":\"3\",\n" +
                "            \"sourceNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf141\",\n" +
                "            \"targetNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf142\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"deabab3d8a3540dfa6aab9b0d4daf04d\",\n" +
                "            \"attributes\":{\n" +
                "\n" +
                "            },\n" +
                "            \"fromPoint\":\"1\",\n" +
                "            \"toPoint\":\"3\",\n" +
                "            \"sourceNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf142\",\n" +
                "            \"targetNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf143\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"53cf8a248e374fb5b2e94679793f5b62\",\n" +
                "            \"attributes\":{\n" +
                "\n" +
                "            },\n" +
                "            \"fromPoint\":\"1\",\n" +
                "            \"toPoint\":\"3\",\n" +
                "            \"sourceNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf143\",\n" +
                "            \"targetNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf144\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String str="{\"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf14\",\"flowNodeDefinitions\":[{\"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf140\",\"name\":\"开始\",\"attributes\":{},\"x\":200,\"y\":200,\"nodeType\":\"start\"},{\"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf141\",\"name\":\"IV黑样本同步\",\"attributes\":{},\"x\":400,\"y\":200,\"nodeType\":\"iv_black_sample_sync\"},{\"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf142\",\"name\":\"指标评估\",\"attributes\":{},\"x\":600,\"y\":200,\"nodeType\":\"metric_evaluate\"},{\"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf143\",\"name\":\"评估结果同步\",\"attributes\":{},\"x\":800,\"y\":200,\"nodeType\":\"evaluate_result_sync\"},{\"id\":\"d2fd00a57d4d43d4b0848fd4b0e8bf144\",\"name\":\"结束\",\"attributes\":{},\"x\":1000,\"y\":200,\"nodeType\":\"end\"}],\"flowLineDefinitions\":[{\"id\":\"63c0bc08b10e4a1f8b53cc4684b77925\",\"attributes\":{},\"fromPoint\":\"1\",\"toPoint\":\"3\",\"sourceNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf140\",\"targetNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf141\"},{\"id\":\"14698a4749394dca972cc8b4b55737e1\",\"attributes\":{},\"fromPoint\":\"1\",\"toPoint\":\"3\",\"sourceNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf141\",\"targetNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf142\"},{\"id\":\"deabab3d8a3540dfa6aab9b0d4daf04d\",\"attributes\":{},\"fromPoint\":\"1\",\"toPoint\":\"3\",\"sourceNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf142\",\"targetNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf143\"},{\"id\":\"53cf8a248e374fb5b2e94679793f5b62\",\"attributes\":{},\"fromPoint\":\"1\",\"toPoint\":\"3\",\"sourceNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf143\",\"targetNodeId\":\"d2fd00a57d4d43d4b0848fd4b0e8bf144\"}]}";

    }
}
