package com.example.demo;

import com.example.demo.factory.FactoryBeanImporter;
import com.example.demo.importter.ImportBeanDefinitionRegistrarTest;
import com.example.demo.importter.ImportSelectorTest;
import com.example.demo.importter.ImportTest;
import com.example.demo.importter.ImportTest3;
import exclude.ExcludeBean;
import exclude.ImportConf;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.nio.charset.StandardCharsets;

@SpringBootApplication(scanBasePackages = {"com.example.demo", "generator"})
@MapperScan(basePackages = "generator.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
@tk.mybatis.spring.annotation.MapperScan(value = "generator.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
//@Import({ImportTest.class, ImportTest3.class, FactoryBeanImporter.class})
//@Import({ImportConf.class})
@Import({ExcludeBean.class})
//@Import({ImportSelectorTest.class})
//@Import({ImportBeanDefinitionRegistrarTest.class})
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

    private static String test(){
        return new StringBuilder().toString();
    }

}
