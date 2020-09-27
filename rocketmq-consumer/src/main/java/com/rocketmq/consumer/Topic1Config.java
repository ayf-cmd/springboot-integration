package com.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 消费者Bean配置
 */
@Slf4j
@Component
@Configuration
public class Topic1Config {

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;
    // 主题
    private String topic = "credit-report-rbs";
    // tag
    private String tag = "*";
    // groupName
    private String groupName = "credit-report-rbs-group";

    @Autowired
    private Topic1ListenerProcessor topic1ListenerProcessor;

    @Bean("topic1")
    public DefaultMQPushConsumer getRocketMQConsumer() {
        try {
            if (StringUtils.isEmpty(groupName)) {
                throw new Exception("groupName is null !!!");
            }
            if (StringUtils.isEmpty(namesrvAddr)) {
                throw new Exception("namesrvAddr is null !!!");
            }
            if (StringUtils.isEmpty(topic)) {
                throw new Exception("topics is null !!!");
            }
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumeThreadMin(consumeThreadMin);
            consumer.setConsumeThreadMax(consumeThreadMax);
            consumer.registerMessageListener(topic1ListenerProcessor);
            /**
             * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
             * 如果非第一次启动，那么按照上次消费的位置继续消费
             */
//            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            // 设置消费模型，集群还是广播，默认为集群
            //consumer.setMessageModel(MessageModel.CLUSTERING);
            // 设置一次消费消息的条数，默认为1条
//            consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
            /**
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
             */
            if (tag == null || "".equals(tag)) {
                consumer.subscribe(topic, "*");
            } else {
                consumer.subscribe(topic, tag);
            }
            consumer.start();
            log.info("consumer is start !!! groupName:{},topic:{},namesrvAddr:{}", groupName, topic, namesrvAddr);
            return consumer;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}