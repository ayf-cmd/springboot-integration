package com.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 监听服务器上的kafka是否有相关的消息发过来
 */
@Component
public class ConsumerDemo {

    @KafkaListener(topics = "demo")
    public void listen (ConsumerRecord<?, ?> record){
        System.err.printf("topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}