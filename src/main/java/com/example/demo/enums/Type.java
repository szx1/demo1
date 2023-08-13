package com.example.demo.enums;

/**
 * @author zengxi.song
 * @date 2022/10/31
 */
public enum Type {

    STRING("字符串"),
    INT("整数"),
    DOUBLE("小数");


    private String desc;

    Type(String desc) {
        System.out.println("构造方法");
        this.desc = desc;
    }

    {
        System.out.println("构造代码块");
    }
    static{
        System.out.println("静态代码块");
    }
}
