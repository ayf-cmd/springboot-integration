package com.rocketmq.mq.producer;

import com.rocketmq.config.RocketMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 */
@Slf4j
public class RocketMqProducer implements Serializable{
    private volatile DefaultMQProducer producer;
    private RocketMqProperties rocketMqProperties;
    private static AtomicInteger messageCount = new AtomicInteger(0);

    public RocketMqProducer(RocketMqProperties rocketMqProperties) {
        this.rocketMqProperties = rocketMqProperties;
    }

    @PostConstruct
    private DefaultMQProducer getInstance() {
        if (this.producer == null) {
            synchronized(this) {
                if (this.producer == null) {
                    initialize();
                }
            }
        }
        return this.producer;
    }
    
    private void initialize() {
        producer = new DefaultMQProducer(rocketMqProperties.getProducer().getGroupName());
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setVipChannelEnabled(false);
        if (rocketMqProperties.getProducer().getMaxMessageSize() != null) {
            producer.setMaxMessageSize(rocketMqProperties.getProducer().getMaxMessageSize());
        }
        if (rocketMqProperties.getProducer().getSendMsgTimeout() != null) {
            producer.setSendMsgTimeout(rocketMqProperties.getProducer().getSendMsgTimeout());
        }
        if (rocketMqProperties.getProducer().getRetryTimesWhenSendFailed() != null) {
            producer.setRetryTimesWhenSendFailed(rocketMqProperties.getProducer().getRetryTimesWhenSendFailed());
        }
        try {
            producer.start();
            log.info(String.format("rocketmq producer start ! groupName:[%s], namesrvAddr:[%s]", rocketMqProperties.getProducer().getGroupName(), rocketMqProperties.getNamesrvAddr()));
        } catch (MQClientException e) {
            log.error("this.producer is error ", e);
        }
    }
    
    public void send(Message msg, SendCallback callback) throws RemotingException, MQClientException, InterruptedException {
        log.debug("Message count:{}, topic: {}, tag: {}, key: {} body: {}",
                messageCount.getAndIncrement(),
                msg.getTopic(),
                msg.getTags(),
                msg.getKeys(),
                msg.getBody());
        getInstance().send(msg, callback);
    }
    
    public void send(Message msg) throws RemotingException, MQClientException, InterruptedException {
        send(msg, new SendCallback() {
            public void onSuccess(SendResult sendResult) {}
            public void onException(Throwable throwable) {
                log.error("Message send error! {}", throwable.getMessage(), throwable);
            }
        });
    }
    
    public void send(String topic, String tag, String key, String body) throws RemotingException, MQClientException, InterruptedException {
        Message message = new Message(topic, tag, key, body.getBytes());
        send(message);
    }
    
    public void close() {
        if (this.producer != null) {
            try {
                this.producer.shutdown();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            this.producer = null;
        }
    }
    
}
