package com.example.demo.controller;

import com.example.demo.proper.SalaxyMetricEvaluateProperties;
import com.example.demo.request.ValidateReq;
import com.example.demo.service.ValidateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zengxi.song
 * @date 2023/11/29
 */
@RequestMapping("validate")
@RestController
@Validated
public class ValidationController {

    @Resource
    private ValidateService validateService;
    @Resource
    private SalaxyMetricEvaluateProperties salaxyMetricEvaluateProperties;

    @GetMapping("test1")
    public void testValidate(ValidateReq req) {
        System.out.println(req.getName());
    }

    @PostMapping("test2")
    public void testValidate2(@RequestBody @Valid ValidateReq req) {
        System.out.println(req.getName());
    }

    @PostMapping("service")
    public void testValidateService(@RequestBody ValidateReq req) {
        validateService.test(req);
    }
}
