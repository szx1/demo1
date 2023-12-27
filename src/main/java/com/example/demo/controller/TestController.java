package com.example.demo.controller;

import com.example.demo.anno.TestAnno;
import com.example.demo.config.ThreadContext;
import com.example.demo.entity.Result;
import com.example.demo.request.ListRequest;
import com.example.demo.util.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import generator.domain.Test;
import generator.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author zengxi.song
 * @date 2022/10/9
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${test.name}")
    private String name;

    @Value("${test.empty:}")
    private String empty;

    static final Map<Integer, Set<String>> map = new HashMap<>();

    @Resource
    TestMapper testMapper;

    @GetMapping("/dir")
    public String test() {
        String s1 = System.getProperty("user.dir");
        String s2 = System.getProperty("catalina.home");

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1.equals(s2));

        return s1;
    }

    @GetMapping("res")
    public String testResource() {
        String fileString = null;
        try {
            AbstractFileResolvingResource resource = new FileUrlResource("./field.json");
            System.out.println(resource.exists());
            if (!resource.exists()) {
                resource = new ClassPathResource("static/field.json");
                System.out.println(resource.exists());
            }
            fileString = FileUtil.readFileAsString(resource.getInputStream(), StandardCharsets.UTF_8.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileString;
    }

    @GetMapping("name")
    public String testName() {
        return name;
    }


    @GetMapping("memory")
    public String memory(Integer num) {
        long l = Runtime.getRuntime().freeMemory();
        System.out.println(l / 1024 / 1024);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < num; j++) {
                String metricCode = "szx123" + j;
                map.computeIfAbsent(i * 1000, k -> new HashSet<>()).add(metricCode);
            }
        }
        long l2 = Runtime.getRuntime().freeMemory();
        System.out.println(l2 / 1024 / 1024);
        System.out.println(l - l2);
        return num.toString();
    }

    @GetMapping("clear")
    public String clear() {
        map.clear();
        return "clear";
    }

    @GetMapping("test/{name}")
    public String testName(Boolean bol, @PathVariable String name) {
        return name;
    }

//    @GetMapping("/test/{name}")
//    public String testName(String str, @PathVariable String name) {
//        System.out.println("2");
//        return name;
//    }

    @GetMapping("test/szx")
    @TestAnno
    public String testSzx() {
        System.out.println(ThreadContext.threadLocal.get());
        return "szx123";
    }

    @GetMapping("test/list")
    @TestAnno
    public String testList(ListRequest request) {
        System.out.println(request.getName());
        System.out.println(request.getTags().size());
        System.out.println(request.getTags());
        return "szx123";
    }

    @GetMapping("relationReference/11")
    public void testRela(@RequestParam List<String> strings) {
        System.out.println(strings);
    }

    @GetMapping("druid")
    public void testDruid() {
        Date date = new Date(1701159530880L);
        testMapper.insertDate(date);
    }

    @GetMapping("druid2")
    public void testDruid2() {
        Example example = new Example(generator.domain.Test.class);
        example.setOrderByClause("id desc limit 1");
        List<generator.domain.Test> tests = testMapper.selectByExample(example);
        System.out.println(tests.get(0));
    }

    @GetMapping("empty")
    public void testEmpty() {
        System.out.println(empty + "123test");
    }

    @GetMapping("json")
    public Result<Object> testJson() {
        Test test = testMapper.selectByPrimaryKey(115);
        return Result.success(test.getNote());
    }

    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        list.add(1, 100);
        System.out.println(list);


        com.sun.management.OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println(operatingSystemMXBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024);
    }

}
