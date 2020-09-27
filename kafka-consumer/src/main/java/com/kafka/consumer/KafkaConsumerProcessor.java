package com.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 异步消息处理
 */
@Component
@Slf4j
public class KafkaConsumerProcessor {

    @KafkaListener(topics = {"#{'${spring.kafka.consumer.topic}'.split(',')}"}, groupId = "#{'${spring.kafka.consumer.groupId}'}")
    public void topic_test(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            Optional message = Optional.ofNullable(record.value());
            Object msg = message.get();
            Object key = record.key();
            System.err.println("receive : " + msg);
        } catch (Exception e) {
            log.error("deal kafka msg error : ", e);
        }finally {
            ack.acknowledge();
        }
    }

}
