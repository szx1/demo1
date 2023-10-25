package com.example.demo;

import cn.tongdun.tianzhou.relation.reference.client.RelationReferenceResponse;
import cn.tongdun.tianzhou.relation.reference.client.RelationReferenceService;
import cn.tongdun.tianzhou.relation.reference.client.dto.RelationBatchAnalysisDTO;
import cn.tongdun.tianzhou.relation.reference.client.qry.RelationAnalysisBatchQry;
import com.example.demo.entity.User;
import com.example.demo.entity.UserTotalDO;
import com.example.demo.enums.Sex;
import com.example.demo.service.TestDataBaseService;
import com.google.common.collect.Lists;
import generator.domain.GroupTest;
import generator.mapper.TestMapper;
import javafx.util.Pair;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@SpringBootTest
class Demo1ApplicationTests {

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private TestMapper testMapper;
    @Resource
    private RelationReferenceService relationReferenceService;
    @Resource
    private TestDataBaseService testDataBaseService;

    @Value("${test.name}")
    private String keyStore;

    @Test
    void contextLoads() {
    }

    @Test
    public void test() {
        List<List<?>> test = testMapper.testTotal();
        List<?> resultList = test.get(0);
        List<generator.domain.Test> result = new ArrayList<>(resultList.size());
        for (Object object : resultList) {
            if (!(object instanceof generator.domain.Test)) {
                throw new RuntimeException("sql invoke error");
            }
            result.add((generator.domain.Test) object);
        }
        Object object = test.get(1).get(0);
        Integer totalCount = (Integer) object;
        System.out.println(test.toString());
    }

    @Test
    public void testGroup() {
        List<GroupTest> map = testMapper.testGroupBy();
        System.out.println(map);
    }

    @Test
    public void testGroup2() {
        List<List<generator.domain.Test>> map = testMapper.testGroupBy2();
        System.out.println(map);
    }

    @Test
    public void testRelation() {
        RelationAnalysisBatchQry qry = new RelationAnalysisBatchQry();
        qry.setComponentIds(Lists.newArrayList("11584"));
        qry.setComponentType("DM_SOURCE");
        RelationReferenceResponse<List<RelationBatchAnalysisDTO>> response = relationReferenceService.batchGetRelationAnalysisList(qry);
        System.out.println(response);
    }

    @Test
    public void testAutoIncrementId() {
        generator.domain.Test test = new generator.domain.Test();
        test.setName("autoIncrement");
        test.setAge(22);
        test.setForIndex(1);
        System.out.println(test.getId());
        testMapper.insertReturnId(test);
        System.out.println(test.getId());
    }

    @Test
    public void testLong2String() {
        Object bean = applicationContext.getBean("tainZhouRelationReferenceSqlSessionFactory");
        System.out.println(bean.getClass().getSimpleName());
        System.out.println(bean.getClass().getName());
        System.out.println(keyStore);
        List<String> ids = testMapper.getIdsByName(Lists.newArrayList("szx"));
        System.out.println(ids.size());
    }

    @Test
    public void testGmtModify() {
        List<Date> times = testMapper.getGmtModifyFromName("szx");
        List<Long> collect = times.stream().map(Date::getTime).collect(Collectors.toList());
        System.out.println(times.size());
    }

    @Test
    public void testWhenCase() {
        List<Date> times = testMapper.getGmtModifyFromName("szx");
        List<Long> collect = times.stream().map(Date::getTime).collect(Collectors.toList());
        System.out.println(times.size());
    }

    @Test
    public void testDate() {
        Date date = new Date(1701159530880L);
//        testMapper.insertDate(date);
        System.out.println(testMapper.selectOneOrderById().getDate().getTime());
    }

    @Test
    public void testTx() {
        List<String> tests = new ArrayList<>();
        testDataBaseService.insert(tests);
        System.out.println(tests);

    }

    @Test
    public void testBean() {
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            if (beanDefinitionName.contains("user") || beanDefinitionName.contains("Import")
                    || beanDefinitionName.contains("test")) {
                System.out.println(beanDefinitionName);
            }
        }
        User bean = applicationContext.getBean(User.class);
    }

    @Test
    public void testLimit() {
        Example example = new Example(generator.domain.Test.class);
//        example.setOrderByClause("id desc limit 1");
        example.createCriteria().andIn("id", Lists.newArrayList("108", "113", "114"));
        example.and().andEqualTo("age", 1);
        List<generator.domain.Test> tests = testMapper.selectByExample(example);
        System.out.println(tests.get(0));
    }

    @Test
    public void testEnumInsert() {
        generator.domain.Test test = new generator.domain.Test("szx", 23, 1, Sex.MALE);
//        testMapper.insertSelective(test);
        Example example = new Example(generator.domain.Test.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", "szx");
        List<generator.domain.Test> list = testMapper.selectByExample(example);
        criteria.andEqualTo("age", 23);
        List<generator.domain.Test> list1 = testMapper.selectByExample(example);
        System.out.println(list.size());
        System.out.println(list1.size());

    }

    @Test
    public void testMessageSource() {
        MessageSource bean = applicationContext.getBean(MessageSource.class);
        bean.getMessage("11", null, Locale.CHINA);
        System.out.println(bean);
    }

    @Test
    public void testBoolean() {
        Boolean aBoolean = testMapper.testBoolean();
        System.out.println(1);
    }

    @Test
    public void testPair() {
        Set<Pair<String, Integer>> pairs = new HashSet<>();
        pairs.add(new Pair("szx", 23));
        pairs.add(new Pair("szx", 100));
        pairs.add(new Pair("1", 100));
        List<generator.domain.Test> list = testMapper.testPair(pairs);
        System.out.println(list.size());
    }

    @Test
    public void testAlter() {
        testMapper.alterTableLength(30);
    }

    @Test
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public void testRecursion() {
        testMapper.testRecursion(Lists.newArrayList(1L, 1L, 2L, 4L, 5L, 6L));
    }

}
