package com.mybatis.service;

import com.github.pagehelper.PageInfo;
import com.mybatis.entity.TestEntity;

public interface TestAnnotationService {

    TestEntity getOne(Long id);

    PageInfo<TestEntity> list(Integer page, Integer limit);

    int add(TestEntity testEntity);

    int testTransactional(TestEntity testEntity);
}
