package com.rocketmq.config;

import com.rocketmq.config.producer.RocketMqProducerProperties;
import com.rocketmq.mq.producer.RocketMqProducer;
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
    @ConfigurationProperties(prefix="rocketmq.producer")
    RocketMqProducerProperties getRocketMqProperties() {
        return new RocketMqProducerProperties();
    }

    /**
     * 生产者
     */
    @Bean(destroyMethod = "close")
    RocketMqProducer getProducer(RocketMqProducerProperties rocketMqProducerProperties) {
        return new RocketMqProducer(rocketMqProducerProperties);
    }

}
