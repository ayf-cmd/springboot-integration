package com.kafka;

import com.kafka.consumer.KafkaConsumerProperties;
import com.kafka.consumer.KafkaListenerProperties;
import com.kafka.producer.KafkaProducerProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KafkaMyProperties {

    /**
     * 指定kafka server的地址，集群配多个，中间，逗号隔开
     */
    private String bootstrapServers;

    /**
     * 生产者配置
     */
    private KafkaProducerProperties producer;

    /**
     * 消费者
     */
    private KafkaConsumerProperties consumer;

    /**
     * 侦听器
     */
    private KafkaListenerProperties listener;

}
