package com.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis客户端
 */
@Slf4j
public class RedisClient {
    private volatile RedissonClient redissonClient;
    private RedisProperties redisProperties;

    public RedisClient(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @PostConstruct
    public RedissonClient getSingletonClient() {
        if (this.redissonClient == null) {
            synchronized(this) {
                if (this.redissonClient == null) {
                    this.redissonClient = initialize();
                }
            }
        }
        return this.redissonClient;
    }

    /**
     * 初始化
     */
    private RedissonClient initialize() {
        log.info("********** initialize redis : {} . ", redisProperties.toString());
        String mode = redisProperties.getMode();
        switch (mode.toLowerCase()) {
            case "single": // 单机
                return initializeSingle();
            case "sentinel": // 主从哨兵
                return initializeSentinel();
            case "cluster": // 集群
                return initializeCluster();
            default:
                throw new RuntimeException("redisType格式错误[single|sentinel|cluster]");
        }
    }

    /**
     * 单机模式
     */
    private RedissonClient initializeSingle() {
        Config config = new Config();
        String node = redisProperties.getSingle().getAddress();
        node = node.startsWith("redis://") ? node : "redis://" + node;
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(node)
                .setDatabase(redisProperties.getDatabase())
                .setTimeout(redisProperties.getTimeout())
                .setConnectTimeout(redisProperties.getPool().getConnTimeout())
                .setConnectionPoolSize(redisProperties.getPool().getSize())
                .setConnectionMinimumIdleSize(redisProperties.getPool().getMinIdle());
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 主从哨兵模式
     */
    private RedissonClient initializeSentinel() {
        Config config = new Config();
        String[] nodes = redisProperties.getSentinel().getNodes().split(",");
        List<String> newNodes = new ArrayList<>(nodes.length);
        Arrays.stream(nodes).forEach((index) -> newNodes.add(index.startsWith("redis://") ? index : "redis://" + index));
        SentinelServersConfig serverConfig = config.useSentinelServers()
                .setDatabase(redisProperties.getDatabase())
                .addSentinelAddress(newNodes.toArray(new String[0]))
                .setMasterName(redisProperties.getSentinel().getMaster())
                .setReadMode(ReadMode.MASTER)
                .setTimeout(redisProperties.getTimeout())
                .setConnectTimeout(redisProperties.getPool().getConnTimeout())
                .setMasterConnectionPoolSize(redisProperties.getPool().getSize())
                .setSlaveConnectionPoolSize(redisProperties.getPool().getSize())
                .setMasterConnectionMinimumIdleSize(redisProperties.getPool().getMinIdle())
                .setSlaveConnectionMinimumIdleSize(redisProperties.getPool().getMinIdle());
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 集群模式
     */
    private RedissonClient initializeCluster() {
        Config config = new Config();
        String[] nodes = redisProperties.getCluster().getNodes().split(",");
        List<String> newNodes = new ArrayList(nodes.length);
        Arrays.stream(nodes).forEach((index) -> newNodes.add(index.startsWith("redis://") ? index : "redis://" + index));
        ClusterServersConfig serverConfig = config.useClusterServers()
                .addNodeAddress(newNodes.toArray(new String[0]))
                .setScanInterval(redisProperties.getCluster().getScanInterval())
                .setIdleConnectionTimeout(redisProperties.getCluster().getSoTimeout())
                .setConnectTimeout(redisProperties.getPool().getConnTimeout())
                .setFailedAttempts(redisProperties.getCluster().getFailedAttempts())
                .setRetryAttempts(redisProperties.getCluster().getRetryAttempts())
                .setRetryInterval(redisProperties.getCluster().getRetryInterval())
                .setMasterConnectionPoolSize(redisProperties.getCluster().getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redisProperties.getCluster().getSlaveConnectionPoolSize())
                .setTimeout(redisProperties.getTimeout());
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }

    public RLock getLock(String lockName) {
        return this.getSingletonClient().getLock(lockName);
    }

	public boolean delete(String key) {
        RBucket bucket = this.getSingletonClient().getBucket(key);
        return bucket.delete();
    }

    public <T> void set(String key, T value) {
        RBucket<T> bucket = this.getSingletonClient().getBucket(key);
        bucket.set(value);
    }

    public <T> void set(String key, T value, long timeout, TimeUnit unit) {
        RBucket<T> bucket = this.getSingletonClient().getBucket(key);
        bucket.set(value, timeout, unit);
    }

    public void sadd(String key, Object value) {
        this.getSingletonClient().getSet(key).add(value);
    }

    public String get(String key) {
        RBucket<String> bucket = this.getSingletonClient().getBucket(key);
        return bucket.get();
    }

    public <T> T get(String key, Class<T> clazz) {
        RBucket<T> bucket = this.getSingletonClient().getBucket(key);
        return bucket.get();
    }

    public RSet<Object> smembers(String key) {
        return this.getSingletonClient().getSet(key);
    }

    public void close() {
        if (this.redissonClient != null) {
            try {
                this.redissonClient.shutdown();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            this.redissonClient = null;
        }
    }

}
