package com.rocketmq.config;

import com.rocketmq.mq.consumer.RocketmqConsumer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ配置
 */
@Configuration
public class RocketMqConfig {

    /**
     * rocketmq配置信息
     */
    @Bean(name = "rocketMqProperties")
    @ConfigurationProperties(prefix="rocketmq")
    RocketMqProperties getRocketMqProperties() {
        return new RocketMqProperties();
    }

    /**
     * 消费者
     */
    @Bean(destroyMethod = "close")
    RocketmqConsumer getConsumer(RocketMqProperties rocketMqProperties) {
        return new RocketmqConsumer(rocketMqProperties, rocketMqProperties.getTopic(), rocketMqProperties.getTag());
    }

}
