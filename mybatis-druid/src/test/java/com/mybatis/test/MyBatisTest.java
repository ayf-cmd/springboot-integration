package com.mybatis.test;

import com.github.pagehelper.PageInfo;
import com.mybatis.MybatisApplication;
import com.mybatis.entity.TestEntity;
import com.mybatis.service.TestAnnotationService;
import com.mybatis.service.TestXmlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = MybatisApplication.class)
public class MyBatisTest {
    @Autowired
    private TestAnnotationService testAnnotationService;
    @Autowired
    private TestXmlService testXmlService;

    /**
     * 测试查询 - 注解方式
     */
    @Test
    public void testGetAnnotation() {
        TestEntity one = testAnnotationService.getOne(1L);
        System.err.println(one);
    }

    /**
     * 测试查询 - XML方式
     */
    @Test
    public void testGetXml() {
        TestEntity one = testXmlService.getOne(1L);
        one = testXmlService.getOne(1L);
        one = testXmlService.getOne(1L);
        one = testXmlService.getOne(1L);
        System.err.println(one);
    }

    /**
     * 测试分页
     */
    @Test
    public void testListPage() {
        PageInfo<TestEntity> testEntityPageInfo = testAnnotationService.list(1, 2);
        List<TestEntity> infoList = testEntityPageInfo.getList();
        for (TestEntity testEntity : infoList) {
            System.err.println(testEntity);
        }
    }

    /**
     * 测试添加
     */
    @Test
    public void testAdd(){
        testAnnotationService.add(new TestEntity(null, UUID.randomUUID().toString().replace("-",""), 200L));
    }

    /**
     * 测试事务
     */
    @Test
    public void testTransactional(){
        testAnnotationService.testTransactional(new TestEntity(null, "testTransactional", 200L));
    }

}
