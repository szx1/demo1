package com.example.demo;

import cn.tongdun.tianzhou.relation.reference.client.RelationReferenceResponse;
import cn.tongdun.tianzhou.relation.reference.client.RelationReferenceService;
import cn.tongdun.tianzhou.relation.reference.client.dto.RelationBatchAnalysisDTO;
import cn.tongdun.tianzhou.relation.reference.client.qry.RelationAnalysisBatchQry;
import com.example.demo.entity.User;
import com.example.demo.entity.UserTotalDO;
import com.example.demo.enums.Sex;
import com.example.demo.importter.ImportBeanDefinitionRegistrarTest;
import com.example.demo.service.TestDataBaseService;
import com.google.common.collect.Lists;
import exclude.ExcludeBean;
import exclude.ExcludeBeanTwo;
import exclude.ImportConf;
import generator.domain.GroupTest;
import generator.domain.TestPartition;
import generator.domain.TestText;
import generator.mapper.TestMapper;
import generator.mapper.TestPartitionMapper;
import generator.mapper.TestTextMapper;
import javafx.util.Pair;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;
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
    @Resource
    private TestTextMapper testTextMapper;
    @Resource
    private TestPartitionMapper testPartitionMapper;


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


    @Test
    public void testGreaterThan() {
        Example example = new Example(generator.domain.Test.class);
        example.createCriteria().andGreaterThan("date", new Timestamp(1688969166000L));
        List<generator.domain.Test> tests = testMapper.selectByExample(example);
        System.out.println(1);
    }

    @Test
    public void testImport() {
        System.out.println(Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bd -> StringUtils.containsIgnoreCase(bd, "Import")
                        || StringUtils.containsIgnoreCase(bd, "excludeBean")).collect(Collectors.toList()));
        Object excludeBean = applicationContext.getBean(ExcludeBean.class);
        System.out.println(excludeBean);
        Object excludeBean2 = applicationContext.getBean(ExcludeBeanTwo.class);
        System.out.println(excludeBean2);
        ImportBeanDefinitionRegistrarTest bean = applicationContext.getBean(ImportBeanDefinitionRegistrarTest.class);
        System.out.println(bean);
    }

    @Test
    public void testJson() {
        generator.domain.Test record = new generator.domain.Test();
        String json = "{\n" +
                "        \"metricCode\":\"\",\n" +
                "        \"metricName\":\"\",\n" +
                "        \"valueType\":\"\",\n" +
                "        \"avgPSI\":\"\",\n" +
                "        \"dailyPSI\":{\n" +
                "            \"2023-01-01\":11\n" +
                "        },\n" +
                "        \"binningInfo\":{\n" +
                "           \n" +
                "        }\n" +
                "    }";
        String json1 = "{\n" +
                "        \"algorithms\":[\n" +
                "            \"STATISTICAL_FUNC\",\n" +
                "            \"QUANTILE\",\n" +
                "            \"CALL_VOLUME\",\n" +
                "            \"PSI\",\n" +
                "            \"IV\"\n" +
                "        ],\n" +
                "        \"binningMethod\":\"EQUAL_INTERVAL\",\n" +
                "        \"metricSelection\":{\n" +
                "            \"filterMethod\":\"METRIC_TYPE\",\n" +
                "            \"filterCodes\":[\n" +
                "                \"\",\n" +
                "                \"\"\n" +
                "            ]\n" +
                "        },\n" +
                "        \"ivBlackSample\":{\n" +
                "            \"dataTableId\":\"\",\n" +
                "            \"bizIdField\":\"\"\n" +
                "        },\n" +
                "        \"dataTimeRange\":{\n" +
                "            \"startTime\":165432323124,\n" +
                "            \"endTime\":46534143254\n" +
                "        }\n" +
                "    }";
        String json3 = "{\n" +
                "    \"metricCode\":\"dfsgffsf\",\n" +
                "    \"metricName\":\"这是一个指标名称\",\n" +
                "    \"valueType\":\"String\",\n" +
                "    \"totalNum\":1,\n" +
                "    \"defaultValue\":\"12345\",\n" +
                "    \"defaultValueNum\":1,\n" +
                "    \"defaultValuePercentage\":\"10%\",\n" +
                "    \"minValue\":12,\n" +
                "    \"maxValue\":56,\n" +
                "    \"avg\":13,\n" +
                "    \"variance\":60,\n" +
                "    \"skewness\":11,\n" +
                "    \"kurtosis\":22\n" +
                "}";
        record.setName("szx");
        record.setAge(23);
        record.setNote(json1);
//        testMapper.insert(record);
        System.out.println(json.getBytes(StandardCharsets.UTF_8).length);
        System.out.println(json1.getBytes(StandardCharsets.UTF_8).length);
        System.out.println(json3.getBytes(StandardCharsets.UTF_8).length);
    }

    @Test
    public void testText() throws InterruptedException {
        String json = "{\n" +
                "    \"metricCode\":\"dfsgffsf\",\n" +
                "    \"metricName\":\"这是一个指标名称\",\n" +
                "    \"valueType\":\"String\",\n" +
                "    \"totalNum\":1,\n" +
                "    \"defaultValue\":\"12345\",\n" +
                "    \"defaultValueNum\":1,\n" +
                "    \"defaultValuePercentage\":\"10%\",\n" +
                "    \"minValue\":12,\n" +
                "    \"maxValue\":56,\n" +
                "    \"avg\":13,\n" +
                "    \"variance\":60,\n" +
                "    \"skewness\":11,\n" +
                "    \"kurtosis\":22\n" +
                "}";
        StringBuilder text = new StringBuilder(json);
        for (int i = 0; i < 3; i++) {
            text.append(json);
        }
        String s = text.toString();
        System.out.println(s.getBytes(StandardCharsets.UTF_8).length);
        List<TestText> insertData = new ArrayList<>();
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        boolean flag = true;
        for (int i = 0; i < 3000; i++) {
//            if (i < 200000 && i % 30000 == 0) {
//                uuid = UUID.randomUUID().toString();
//                System.out.println(uuid);
//            }
//            if (i > 200000 && flag) {
//                uuid = UUID.randomUUID().toString();
//                System.out.println(uuid);
//                flag = false;
//            }
            TestText testText = new TestText();
            testText.setUuid(uuid);
            testText.setText1(s);
            testText.setText2(s);
            testText.setText3(s);
            testText.setText4(s);
            testText.setText5("test" + i % 100);
            insertData.add(testText);
            if (insertData.size() == 100) {
                testTextMapper.insertList(insertData);
                insertData.clear();
//                System.out.println("sleep 100 ms");
                Thread.sleep(100);
            }
        }

    }

    @Test
    public void testPartition() {
        List<TestPartition> insertData = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            TestPartition testPartition = new TestPartition();
            testPartition.setAge(i);
            if (i % 100 == 0) {
                testPartition.setTaskUuid("123456789123456" + i + "23985" + i + "123456");
            } else {
                testPartition.setTaskUuid(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            testPartition.setName("szx" + i);
            insertData.add(testPartition);
            if (insertData.size() == 100) {
                testPartitionMapper.insertList(insertData);
                insertData.clear();
            }
        }

    }

    public static void main(String[] args) {
        double badRate = 40d / 430d;
        double goodRate = 1691d / 8225d;
        System.out.println((badRate - goodRate) * Math.log(badRate / goodRate));
    }

    private static double prefix(double n) {
        return (n * (n + 1)) / ((n - 1) * (n - 2) * (n - 3));
    }

    private static double suffix(double n) {
        return (3 * Math.pow(n - 1, 2)) / ((n - 2) * (n - 3));
    }


}
