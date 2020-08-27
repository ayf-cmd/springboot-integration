package com.rocketmq.consumer;

import com.rocketmq.mq.consumer.RocketmqEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 异步消息处理
 */
@Component
@Slf4j
public class ConsumerProcessor {

    @EventListener(condition = "#event.msgs[0].topic == T(com.rocketmq.config.RocketMqTraceConfig).TRACE_TOPIC")
    public void process(RocketmqEvent event) throws Exception {
        try {
            String key = event.getKeys(0);
            String topic = event.getTopic(0);
            String tag = event.getTag(0);
            log.info("receive rocketmq message : topic {} . tag {} . key {} . 不进行处理，丢弃任务。 ", topic, tag, key);
        } catch (Exception e) {
            log.error("error : ", e);
        }
    }

}
