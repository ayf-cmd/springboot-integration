package com.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.service.ITestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class UserController {

    @Reference(version = "1.0")
    private ITestService iTestService;

    @RequestMapping("/sayHello")
    public String sayHello(String name){
        return iTestService.sayHello(name);
    }

}