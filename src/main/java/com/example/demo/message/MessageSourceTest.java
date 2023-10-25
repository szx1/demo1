package com.example.demo.message;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author zengxi.song
 * @date 2023/8/23
 */
@Component
public class MessageSourceTest implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MessageSource bean = applicationContext.getBean(MessageSource.class);
//        bean.getMessage("11",null, Locale.CHINA);
//        System.out.println(bean);
    }
}
