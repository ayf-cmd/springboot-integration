package com.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatis.entity.TestEntity;
import com.mybatis.mapper.TestXmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestXmlServiceImpl implements TestXmlService {
    @Autowired
    private TestXmlMapper testMapper;

    @Override
    public TestEntity getOne(Long id) {
        return testMapper.getOne(id);
    }

    @Override
    public PageInfo<TestEntity> list(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<TestEntity> list = testMapper.list();
        PageInfo<TestEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
