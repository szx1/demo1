package com.example.demo;

import com.example.demo.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import generator.domain.Org;
import generator.mapper.OrgMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengxi.song
 * @date 2023/10/22
 */
@SpringBootTest
public class OrgTest {

    @Resource
    private OrgMapper orgMapper;

    @Test
    public void testRecursion() {
        List<Org> orgs = orgMapper.testRecursion();
        System.out.println(orgs);
    }


    @Test
    public void testGroupBy() {
        List<Org> orgs = orgMapper.testGroupBy();
        System.out.println(orgs);
    }

    public static void main(String[] args) {
        Map<String,Object> map1=new HashMap<>();
        map1.put("szx",new Org());
        Map<String, Object> map2 = new HashMap<>(map1);
        ((Org)map2.get("szx")).setName("szx");
        System.out.println(1);
        String jsonStr = JsonUtil.jsonSerialize(map1);
        Map<String, Object> originalExtendMap = JsonUtil.jsonDeserialize(jsonStr, new TypeReference<Map<String, Object>>() {
        });

        Map<String, Org> newExtendMap = JsonUtil.jsonDeserialize(jsonStr, new TypeReference<Map<String, Org>>() {
        });
        ((Org)newExtendMap.get("szx")).setName("newExtendMap");
        System.out.println(1);
    }
}
