package com.jasypt.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
@ConfigurationProperties(prefix = "value")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PropertiesConfig implements Serializable {

    private String test;

}
