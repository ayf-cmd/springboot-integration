package com.mybatis.mapper;

import com.mybatis.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * xml方式
 */
@Mapper
public interface TestXmlMapper {

    TestEntity getOne(Long id);

    List<TestEntity> list();

}
