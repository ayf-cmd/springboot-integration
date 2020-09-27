package com.rocketmq.config.producer;

import lombok.*;

import java.io.Serializable;

/**
 * 生产者配置信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RocketMqProducerProperties implements Serializable {

    /**
     * ConfigMq 地址
     */
    private String namesrvAddr;

    /**
     * 主题
     */
    private String topic;
    /**
     * tag
     */
    private String tag;
    /**
     * 发送者的groupName
     */
    private String groupName;
    /**
     * 允许的最大消息体
     */
    private Integer maxMessageSize;
    /**
     * 发送超时时长
     */
    private Integer sendMsgTimeout;
    /**
     * 重试次数
     */
    private Integer retryTimesWhenSendFailed;

}
