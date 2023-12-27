package com.example.demo.service;

import com.example.demo.request.ValidateReq;

import javax.validation.Valid;

/**
 * @author zengxi.song
 * @date 2023/11/30
 */
public interface ValidateService {

    public void test(@Valid ValidateReq req);
}
