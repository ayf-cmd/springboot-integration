package com.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * kafka发送监听
 */
@Slf4j
public class CallbackKafka implements Callback {
    private String topic;
    private String key;

    /**
     * 异步发送消息回调类
     * @param topic   : 主题
     * @param key     : key
     */
    public CallbackKafka(String topic, String key) {
        this.topic = topic;
        this.key = key;
    }

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            this.onFailure(e);
        } else {
            this.onSuccess();
        }
    }

    public void onFailure(Exception ex) {
        log.error("[sendfail] kafka send msg fail. ", ex);
    }

    public void onSuccess() {
        log.info("kafka send msg success. topic:[ {} ], key:[ {} ] . ", topic, key);
    }

}
