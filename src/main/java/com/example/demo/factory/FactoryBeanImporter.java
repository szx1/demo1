package com.example.demo.factory;

import com.example.demo.entity.User;
import com.example.demo.importter.ImportTest;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zengxi.song
 * @date 2023/6/5
 */
public class FactoryBeanImporter implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = new RootBeanDefinition();
//        beanDefinition.setBeanClassName(User.class.getName());
//        registry.registerBeanDefinition("userImport", beanDefinition);
    }

    public static void main(String[] args) {
        System.out.println(reverseWords("a"));
        System.out.println(reverseWords(" hello world "));
    }


    public static String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int right = s.length() - 1;
        while (right >= 0) {
            while (right >= 0 && s.charAt(right) == ' ') {
                right--;
            }
            if (right < 0) {
                return sb.substring(0, sb.length() - 1);
            }
            int left = right - 1;
            while (left >= 0 && s.charAt(left) != ' ') {
                left--;
            }
            sb.append(s, left + 1, right + 1).append(' ');
            right = left;
        }
        return sb.substring(0, sb.length() - 1);
    }
}
