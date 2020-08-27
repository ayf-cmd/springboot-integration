package com.jasypt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

@Configuration
public class PropertiesValue {

    /**
     * 使用 @Value方式读取
     */
    @Value("${value.test}")
    private String test;

    public String getValue() {
        return test;
    }

    /**
     * 解决中文乱码
     */
    @Value("${config.test:''}")
    public void setQueryOrgan(String test){
        try {
            test = new String(test.getBytes("ISO8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.err.println("test : " + test);
    }

}
