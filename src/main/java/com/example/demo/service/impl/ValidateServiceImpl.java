package com.example.demo.service.impl;

import com.example.demo.request.ValidateReq;
import com.example.demo.service.ValidateService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author zengxi.song
 * @date 2023/11/30
 */
@Service
@Validated
public class ValidateServiceImpl implements ValidateService {

    @Override
    public void test(@Valid ValidateReq req) {
        System.out.println(1);
    }
}
