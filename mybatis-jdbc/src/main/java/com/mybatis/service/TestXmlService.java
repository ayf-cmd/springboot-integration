package com.mybatis.service;

import com.github.pagehelper.PageInfo;
import com.mybatis.entity.TestEntity;

public interface TestXmlService {

    TestEntity getOne(Long id);

    PageInfo<TestEntity> list(Integer page, Integer limit);

}
