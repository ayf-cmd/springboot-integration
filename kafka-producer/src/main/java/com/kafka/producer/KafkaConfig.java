package com.kafka.producer;

import com.kafka.KafkaMyProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * KAFKA配置信息
 */
@Configuration
//@ConditionalOnProperty(prefix="mq",name = "type", havingValue = "KAFKA")
public class KafkaConfig {

    /**
     * kafka配置信息
     */
    @Bean(name = "kafkaMyProperties")
    @ConfigurationProperties(prefix="spring.kafka")
    KafkaMyProperties getKafkaProperties() {
        return new KafkaMyProperties();
    }

    /**
     * 创建生产者
     */
    @Bean(destroyMethod = "close")
    MyKafkaProducer getProducer(KafkaMyProperties kafkaMyProperties) {
        return new MyKafkaProducer(kafkaMyProperties);
    }
    
}
