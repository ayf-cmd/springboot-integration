package com.rocketmq.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

@Slf4j
public class AsyncSendMessageCallback implements SendCallback {
    private String topic;
    private String tag;
    private String key;
    private String msg;

    /**
     * 异步发送消息回调类
     * @param topic   : 主题
     * @param tag     : tag
     * @param key     : key
     * @param msg     : 信息
     */
    public AsyncSendMessageCallback(String topic, String tag, String key, String msg) {
        this.topic = topic;
        this.tag = tag;
        this.key = key;
        this.msg = msg;
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("send msg success. topic:[ {} ], tag:[ {} ], key:[ {} ]", topic, tag, key);
    }

    @Override
    public void onException(Throwable e) {
        log.error("send fail {} . ", e);
    }

}
