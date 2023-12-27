package com.example.demo.controller;

import com.github.pagehelper.PageHelper;
import generator.domain.TestText;
import generator.mapper.TestTextMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zengxi.song
 * @date 2023/11/23
 */
@RequestMapping("testText")
@RestController
public class TestTextController {

    @Resource
    private TestTextMapper testTextMapper;

    @GetMapping("uuid")
    public List<String> test(String uuid) {
        Example example = new Example(TestText.class);
        example.createCriteria().andEqualTo("uuid", uuid);
        example.setOrderByClause("id limit 1,10");
        List<String> collect = testTextMapper.selectByExample(example).stream().map(TestText::getText1).collect(Collectors.toList());
        System.out.println(collect.size());
        return collect;
    }
}
