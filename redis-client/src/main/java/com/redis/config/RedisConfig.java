package com.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * redis配置
 */
@Configuration
@EnableAspectJAutoProxy
public class RedisConfig {

	@Bean
	@ConfigurationProperties(prefix = "redis")
	RedisProperties getRedisConfig() {
		return new RedisProperties();
	}
	
	@Bean(destroyMethod = "close")
    RedisClient getRedisClient(RedisProperties config) {
		return new RedisClient(config);
	}

	@Bean
    RedisLockAspect getAspects(RedisClient redisClient) {
		return new RedisLockAspect(redisClient);
	}

}
