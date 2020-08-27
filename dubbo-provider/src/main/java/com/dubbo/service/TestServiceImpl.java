package com.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = ITestService.class, version = "1.0", retries = 0)
public class TestServiceImpl implements ITestService{

    @Override
    public String sayHello(String name) {
        return "hello:"+name;
    }

}