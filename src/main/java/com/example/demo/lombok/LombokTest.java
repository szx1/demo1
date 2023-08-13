package com.example.demo.lombok;

import lombok.Builder;

/**
 * @author zengxi.song
 * @date 2023/8/2
 */
public class LombokTest {

    @Builder.Default
    private Integer age = 0;


    public static void main(String[] args) {
        LombokTest lombokTest = new LombokTest();
        System.out.println(lombokTest.age);
    }
}
