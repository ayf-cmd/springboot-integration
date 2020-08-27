package com.rocketmq.mq.consumer;

import com.rocketmq.config.RocketMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 消费者
 */
@Slf4j
public class RocketmqConsumer {
    private DefaultMQPushConsumer consumer;
    private RocketMqProperties rocketMqProperties;
    private String topic;
    private String tag;
    @Autowired
    private ApplicationEventPublisher publisher;

    public RocketmqConsumer(RocketMqProperties rocketMqProperties, String topic, String tag) {
        this.rocketMqProperties = rocketMqProperties;
        this.topic = topic;
        this.tag = tag;
    }

    public RocketmqConsumer(RocketMqProperties rocketMqProperties, String topic) {
        this.rocketMqProperties = rocketMqProperties;
        this.topic = topic;
    }
    
    @PostConstruct
    private void initialize() {
        consumer = new DefaultMQPushConsumer(rocketMqProperties.getConsumer().getGroupName());
        consumer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        consumer.setVipChannelEnabled(false);
        if (rocketMqProperties.getConsumer().getConsumeThreadMin() != null) {
            consumer.setConsumeThreadMin(rocketMqProperties.getConsumer().getConsumeThreadMin());
        }
        if (rocketMqProperties.getConsumer().getConsumeThreadMax() != null) {
            consumer.setConsumeThreadMax(rocketMqProperties.getConsumer().getConsumeThreadMax());
        }
        try {
            if (tag == null || "".equals(tag)) {
                consumer.subscribe(topic, "*");
            } else {
                consumer.subscribe(topic, tag);
            }
            consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
                try {
                    if(msgs.size()==0) {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    this.publisher.publishEvent(new RocketmqEvent(msgs, consumer));
                } catch (Exception e) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            log.info("************* consumer.start . topic : {} . nameserver : {} . groupname : {} . ", topic, rocketMqProperties.getNamesrvAddr(), rocketMqProperties.getConsumer().getGroupName());
            consumer.start();
        } catch (MQClientException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void close() {
        if (this.consumer != null) {
            try {
                this.consumer.shutdown();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            this.consumer = null;
        }
    }

}
