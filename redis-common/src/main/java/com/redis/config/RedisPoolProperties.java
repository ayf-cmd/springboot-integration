package com.redis.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RedisPoolProperties {

    /**
     *
     */
    private int minIdle;
    /**
     * 连接超时时间
     */
    private int connTimeout;
    /**
     * 池大小
     */
    private int size;

}
