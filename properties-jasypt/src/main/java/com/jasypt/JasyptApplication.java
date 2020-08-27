package com.jasypt;

import com.jasypt.config.PropertiesConfig;
import com.jasypt.config.PropertiesEnvironment;
import com.jasypt.config.PropertiesValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JasyptApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(JasyptApplication.class, args);

        PropertiesValue propertiesValue = applicationContext.getBean(PropertiesValue.class);
        System.err.println("value 方式读取 : " + propertiesValue.getValue());

        PropertiesConfig propertiesConfig = applicationContext.getBean(PropertiesConfig.class);
        System.err.println("config 方式读取 : " + propertiesConfig.getTest());

        PropertiesEnvironment environment = applicationContext.getBean(PropertiesEnvironment.class);
        System.err.println("Environment 方式读取 : " + environment.getValue());

    }
    
}
