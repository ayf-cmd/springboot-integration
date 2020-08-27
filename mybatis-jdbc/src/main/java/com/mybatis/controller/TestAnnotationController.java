package com.mybatis.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mybatis.entity.TestEntity;
import com.mybatis.service.TestAnnotationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/annotation")
public class TestAnnotationController {
    @Autowired
    private TestAnnotationService testAnnotationService;

    @GetMapping(value = "/get")
    public String get() {
        return testAnnotationService.getOne(1L).toString();
    }

    @GetMapping(value = "/list")
    public String list(Integer page, Integer limit) {
        PageInfo<TestEntity> testEntityPageInfo = testAnnotationService.list(page, limit);
        List<TestEntity> list = testEntityPageInfo.getList();
        for (TestEntity testEntity : list) {
            log.error(testEntity.toString());
        }
        return JSON.toJSONString(testEntityPageInfo);
    }

}
