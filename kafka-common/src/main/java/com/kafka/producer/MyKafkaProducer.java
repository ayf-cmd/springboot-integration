package com.kafka.producer;

import com.kafka.KafkaMyProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Properties;

/**
 * 生产者
 */
@Slf4j
public class MyKafkaProducer implements Serializable{
    private volatile KafkaProducer<String, String> kafkaProducer;
    private KafkaMyProperties kafkaMyProperties;

    public MyKafkaProducer(KafkaMyProperties kafkaMyProperties) {
        this.kafkaMyProperties = kafkaMyProperties;
    }

    @PostConstruct
    public KafkaProducer<String, String> getInstance() {
        if (this.kafkaProducer == null) {
            synchronized(this) {
                if (this.kafkaProducer == null) {
                    initialize();
                }
            }
        }
        return this.kafkaProducer;
    }

    // 初始化kafkaProducer
    private void initialize() {
        try {
            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaMyProperties.getBootstrapServers());
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaMyProperties.getProducer().getKeySerializer());
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaMyProperties.getProducer().getValueSerializer());
            properties.put(ProducerConfig.ACKS_CONFIG, kafkaMyProperties.getProducer().getAcks());
            properties.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaMyProperties.getProducer().getBatchSize());
            properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaMyProperties.getProducer().getBufferMoney());
            properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, kafkaMyProperties.getProducer().getMaxRequestSize());
            kafkaProducer = new KafkaProducer<String, String>(properties);
            log.info("kafka producer init finish ... ");
        } catch (Exception e) {
            log.error(" kafka producer init error : ", e);
        }
    }

    public void close() {
        if (this.kafkaProducer != null) {
            try {
                this.kafkaProducer.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            this.kafkaProducer = null;
        }
    }
    
}
