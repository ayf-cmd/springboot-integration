package com.mybatis.mapper;

import com.mybatis.entity.TestEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 注解方式
 */
public interface TestAnnotationMapper {

    @Select("select * from t_order where id = #{id}")
    TestEntity getOne(Long id);

    @Select("select * from t_order")
    List<TestEntity> list();

    @Insert("insert into t_order(order_id, order_money) values(#{orderId},#{orderMoney})")
    int add(TestEntity testEntity);

}
