package com.kafka.producer;

import com.kafka.KafkaMyProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 发送kafka消息
 */
//@ConditionalOnProperty(prefix="mq",name = "type", havingValue = "KAFKA")
@Slf4j
@Component
public class SendMessageKafka {
    @Autowired
    private MyKafkaProducer myKafkaProducer;
    @Autowired
    private KafkaMyProperties kafkaProperties;

    static ThreadPoolExecutor sendMsgExecutor = new ThreadPoolExecutor(10, 20, 60,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100));

    /**
     * 发送消息
     */
    public void sendMsg(String msg, String key) {
        sendMsgExecutor.submit(new Task(myKafkaProducer, msg, kafkaProperties.getProducer().getTopic(), key));
    }

    private static class Task implements Runnable {
        private MyKafkaProducer myKafkaProducer;
        private String msg;
        private String topic;
        private String key;

        Task(MyKafkaProducer myKafkaProducer, String msg, String topic, String key) {
            this.myKafkaProducer = myKafkaProducer;
            this.msg = msg;
            this.key = key;
            this.topic = topic;
        }

        @Override
        public void run() {
            log.info("kafka sendmsg start ，topic[{}] ", topic);
            myKafkaProducer.getInstance().send(new ProducerRecord(topic, key, msg), new CallbackKafka(
                    topic, key
            ));
        }

    }

}
