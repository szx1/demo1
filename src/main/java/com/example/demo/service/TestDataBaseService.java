package com.example.demo.service;

import com.example.demo.request.ListRequest;
import generator.domain.Test;
import generator.mapper.TestMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zengxi.song
 * @date 2023/2/8
 */
@Service
public class TestDataBaseService {
    @Resource
    private TestMapper testMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insert(List<String> uuids) {
        try {
            Test test = testMapper.selectByPrimaryKey(1L);
            Test test1 = new Test();
            BeanUtils.copyProperties(test, test1);
            test1.setId(100);
            uuids.add("start");
            testMapper.insert(test1);
            uuids.add("one");
            testMapper.insert(test);
            uuids.add("two");
        }finally {
            System.out.println("finally");
        }

    }
}
