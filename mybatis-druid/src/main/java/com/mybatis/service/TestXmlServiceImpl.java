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
    private TestXmlMapper testXmlMapper;

    @Override
    public TestEntity getOne(Long id) {
        return testXmlMapper.getOne(id);
    }

    @Override
    public PageInfo<TestEntity> list(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<TestEntity> list = testXmlMapper.list();
        PageInfo<TestEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
