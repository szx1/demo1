package com.example.demo.importter;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zengxi.song
 * @date 2023/5/15
 */
public class ImportTest implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println(importingClassMetadata.getClass());
        BeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClassName(ImportTest2.class.getName());
        registry.registerBeanDefinition("importTest2", beanDefinition);
    }

    public static class ImportTest2 implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            BeanDefinition beanDefinition = new RootBeanDefinition();
            beanDefinition.setBeanClassName(ImportTest2.class.getName());
            registry.registerBeanDefinition("importTest2", beanDefinition);
        }
    }

    public static void main(String[] args) {
        System.out.println(ImportTest2.class.getName());
        System.out.println(ImportTest2.class.getSimpleName());
    }
}
