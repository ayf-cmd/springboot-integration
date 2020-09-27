package com.rocketmq.mq.producer;

import com.rocketmq.config.SpringContextHolder;
import com.rocketmq.config.producer.RocketMqProducerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 发送消息至RocketMQ
 */
@Slf4j
public class SendContext {
    private static RocketMqProducer rocketMqProducer = SpringContextHolder.getBean(RocketMqProducer.class);
    private static RocketMqProducerProperties rocketMqProducerProperties = SpringContextHolder.getBean(RocketMqProducerProperties.class);
    private static ThreadPoolExecutor sendMsgExecutor = new ThreadPoolExecutor(20, 50, 60000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(3000));

    /**
     * 异步发送消息给dataService
     * @param topic          : 主题
     * @param tag            : tag
     * @param key            : key
     * @param msg            : msg
     */
    public static void sendMsg(String topic, String tag, String key, String msg) {
        sendMsgExecutor.submit(new Task(topic, tag, key, msg));
    }
    public static void sendMsg(String key, String msg) {
        sendMsgExecutor.submit(new Task(rocketMqProducerProperties.getTopic(), rocketMqProducerProperties.getTag(), key, msg));
    }
    public static void sendMsg(String msg) {
        sendMsgExecutor.submit(new Task(rocketMqProducerProperties.getTopic(), rocketMqProducerProperties.getTag(), UUID.randomUUID().toString().replace("-", ""), msg));
    }

    private static class Task implements Runnable {
        private String topic;
        private String tag;
        private String key;
        private String msg;

        Task(String topic, String tag, String key, String msg) {
            this.topic = topic;
            this.key = key;
            this.tag = tag;
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                Message message = new Message(topic, tag, key, msg.getBytes());
                rocketMqProducer.send(message, new AsyncSendMessageCallback(topic, tag, key, msg));
            } catch (RemotingException | MQClientException | InterruptedException e) {
                log.error("send msg error,key=[{}] : ",key, e);
            }
        }
    }

}
