package com.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatis.entity.TestEntity;
import com.mybatis.mapper.TestAnnotationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TestAnnotationServiceImpl implements TestAnnotationService {
    @Autowired
    private TestAnnotationMapper testAnnotationMapper;

    @Override
    public TestEntity getOne(Long id) {
        return testAnnotationMapper.getOne(id);
    }

    @Override
    public PageInfo<TestEntity> list(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<TestEntity> list = testAnnotationMapper.list();
        PageInfo<TestEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int add(TestEntity testEntity) {
        return testAnnotationMapper.add(testEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    @Override
    public int testTransactional(TestEntity testEntity) {
        testAnnotationMapper.add(testEntity);
        System.err.println("add success");
        int i = 1/ 0;
        int add = testAnnotationMapper.add(testEntity);
        return add;
    }

}
