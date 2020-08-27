package com.jasypt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropertiesEnvironment {

    @Autowired
    private Environment env;

    public String getValue() {
        return env.getProperty("value.test");
    }

}
