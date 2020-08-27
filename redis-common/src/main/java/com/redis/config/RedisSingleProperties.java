package com.redis.config;

import lombok.Data;
import lombok.ToString;

/**
 * redis单机配置信息
 */
@Data
@ToString
public class RedisSingleProperties {

    private String address;

}
