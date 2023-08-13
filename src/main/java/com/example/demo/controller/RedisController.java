package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.test.OderTest;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author zengxi.song
 * @date 2021/12/19
 */
@RequestMapping("test")
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private List<OderTest> orderTestList;

    @GetMapping({"/test"})
    public String test(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") int n, String name) {
//        HttpSession session = request.getSession(true);
        System.out.println(n + 1);
        return name;
    }

    @PostMapping("/test3")
    public String testForm(User user) {
        return user.getName();
    }

    @PostMapping("/test2")
    public String testMap(@RequestBody Map<String, String> map, @RequestParam String name, String uuid) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "   " + entry.getValue());
        }
        System.out.println(name + uuid);
        return "hello";
    }

    @PostMapping("/test4")
    public String testParam(String name) {
        return name;
    }

    @GetMapping("/test5")
    public String testFive(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getSession());
        System.out.println(request.getSession().getId());
        return "ok";
    }

    @GetMapping("https")
    public String testHttps() {
        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory();
        return "success";
    }

    @GetMapping("order")
    public void testOrder(){
        orderTestList.forEach(item-> System.out.println(item.test()));
    }
}
