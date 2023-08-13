package com.example.demo.test;

/**
 * @author zengxi.song
 * @date 2023/7/4
 */
public class Child implements Parent<Integer> {

    @Override
    public Integer get(Integer i) {
        return i;
    }
}
