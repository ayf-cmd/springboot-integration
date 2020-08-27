package com.configcenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesValue {

    /**
     * 使用 @Value方式读取
     */
    @Value("${test.value}")
    private String test;

    public String getValue() {
        return test;
    }

}
