package com.redis.config;

import lombok.Data;
import lombok.ToString;

/**
 * redis主从哨兵配置信息
 */
@Data
@ToString
public class RedisSentinelProperties {

    /**
     * 哨兵master 名称
     */
    private String master;

    /**
     * 哨兵节点
     */
    private String nodes;

    /**
     * 哨兵配置
     */
    private boolean masterOnlyWrite = true;

}
