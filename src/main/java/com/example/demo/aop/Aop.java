package com.example.demo.aop;

import com.example.demo.config.ThreadContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author zengxi.song
 * @date 2023/2/19
 */
@Aspect
@Component
public class Aop {


    @Before("@annotation(com.example.demo.anno.TestAnno)")
    public void printAop(){
        ThreadContext.threadLocal.set("threadLocal");
        System.out.println("aop");
    }
}
