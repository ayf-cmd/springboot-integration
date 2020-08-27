package com.redis.config;

import lombok.Data;

/**
 * redis配置信息
 */
@Data
public class RedisProperties {

    /**
     * redis集群模式
     * single    : 单机
     * sentinel : 主从哨兵
     * cluster  : 集群
     */
    private String mode = "single";
    private int database = 0;
    private int timeout = 3000;
    private String password;

    /**
     * 池配置
     */
    private RedisPoolProperties pool;

    /**
     * 单机信息配置
     */
    private RedisSingleProperties single;

    /**
     * 哨兵配置
     */
    private RedisSentinelProperties sentinel;

    /**
     * 集群 信息配置
     */
    private RedisClusterProperties cluster;

    @Override
    public String toString() {
        return "RedisProperties{" +
                "mode='" + mode + '\'' +
                ", database=" + database +
                ", timeout=" + timeout +
                ", pool=" + pool +
                ", single=" + single +
                ", sentinel=" + sentinel +
                ", cluster=" + cluster +
                '}';
    }

}
