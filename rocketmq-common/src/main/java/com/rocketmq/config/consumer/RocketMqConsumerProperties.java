package com.rocketmq.config.consumer;

import lombok.*;

import java.io.Serializable;

/**
 * 消费者配置信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RocketMqConsumerProperties implements Serializable {

    /**
     * 主题
     */
    private String topic;
    /**
     * tag
     */
    private String tag;
    /**
     * 消费者的groupName
     */
    private String groupName;
    /**
     * 消费线程池最小数量
     */
    private Integer consumeThreadMin;
    /**
     * 消费线程池最大数量
     */
    private Integer consumeThreadMax;

}
