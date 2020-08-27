package com.mybatis.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
@ConfigurationProperties(prefix = "druid")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DruidDataSourceConfig implements Serializable {

    public String name;
    public String url;
    public String username;
    public String password;
    public String type;
    public String driverClassName;
    public String filters;
    public int maxActive;
    public int initialSize;
    public int maxWait;
    public int minIdle;
    public int timeBetweenEvictionRunsMillis;
    public int minEvictableIdleTimeMillis;
    public boolean testWhileIdle;
    public String validationQuery;
    public boolean testOnBorrow;
    public boolean testOnReturn;
    public boolean poolPreparedStatements;
    public int maxPoolPreparedStatementPerConnectionSize;
    public int maxOpenPreparedStatements;
    public String connectionProperties;
    public boolean useGlobalDataSourceStat;

    // Druid Monitor 黑名单
    public String druidBlackList = "";
    // Druid Monitor 用户名
    public String druidUserName = "test";
    // Druid Monitor 密码
    public String druidPwd = "test";

}
