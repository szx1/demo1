package com.example.demo.test;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author zengxi.song
 * @date 2023/3/17
 */
@Order(0)
@Component
public class OrderA implements OderTest{
    @Override
    public String test() {
        return "A";
    }
}
