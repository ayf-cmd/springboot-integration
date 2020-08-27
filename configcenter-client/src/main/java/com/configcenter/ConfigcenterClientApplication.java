package com.configcenter;

import com.configcenter.config.PropertiesValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConfigcenterClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConfigcenterClientApplication.class, args);

        PropertiesValue propertiesValue = applicationContext.getBean(PropertiesValue.class);
        System.err.println("value 方式读取 : " + propertiesValue.getValue());

    }

}
