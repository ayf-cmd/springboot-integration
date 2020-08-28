package com.rocketmq.config;

import com.rocketmq.config.consumer.RocketMqConsumerProperties;
import com.rocketmq.config.producer.RocketMqProducerProperties;
import lombok.*;

import java.io.Serializable;

/**
 * rocketmq配置信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RocketMqProperties implements Serializable {

    /**
     * ConfigMq 地址
     */
    private String namesrvAddr;

    /**
     * 消费者
     */
    private RocketMqConsumerProperties consumer;

    /**
     * 生产者
     */
    private RocketMqProducerProperties producer;

}
