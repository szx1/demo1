package com.example.demo.test;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author zengxi.song
 * @date 2023/3/17
 */
@Order(1)
@Component
public class OrderB implements OderTest {
    @Override
    public String test() {
        return "B";
    }
}
