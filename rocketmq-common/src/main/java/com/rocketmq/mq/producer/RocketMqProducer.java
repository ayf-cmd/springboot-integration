package com.rocketmq.mq.producer;

import com.rocketmq.config.RocketMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.admin.ConsumeStats;
import org.apache.rocketmq.common.admin.OffsetWrapper;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 */
@Slf4j
public class RocketMqProducer implements Serializable{
    private volatile DefaultMQProducer producer;
    private DefaultMQAdminExt defaultMQAdminExt;
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

    @PostConstruct
    private DefaultMQAdminExt getInstanceAdmin() {
        if (this.defaultMQAdminExt == null) {
            synchronized(this) {
                if (this.defaultMQAdminExt == null) {
                    initializeAdminExt();
                }
            }
        }
        return this.defaultMQAdminExt;
    }

    /**
     * 获取topic的消息堆积量
     */
    public synchronized long getMsgPileNum(){
        long diffTotal = 0L;
        try{
            // 当消费端未消费时，此方法会报错
            ConsumeStats consumeStats = this.getInstanceAdmin().examineConsumeStats(rocketMqProperties.getProducer().getGroupName());
            List<MessageQueue> mqList = new LinkedList();
            mqList.addAll(consumeStats.getOffsetTable().keySet());
            Collections.sort(mqList);
            // 遍历所有的队列，计算堆积量
            for (MessageQueue mq : mqList) {
                if(rocketMqProperties.getProducer().getTopic().equals(mq.getTopic())){
                    OffsetWrapper offsetWrapper = (OffsetWrapper)consumeStats.getOffsetTable().get(mq);
                    diffTotal += (offsetWrapper.getBrokerOffset() - offsetWrapper.getConsumerOffset());
                }
            }
            return diffTotal;
        }catch(Throwable e){
            return -1L;
        }
    }

    // 初始化监控客户端
    private void initializeAdminExt() {
        try {
            defaultMQAdminExt = new DefaultMQAdminExt();
            defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
            defaultMQAdminExt.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
            defaultMQAdminExt.start();
        }catch (Exception e){
            log.error("监控客户端启动失败...", e);
        }
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
            try {
                defaultMQAdminExt.shutdown();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            this.producer = null;
            this.defaultMQAdminExt = null;
        }
    }
    
}
