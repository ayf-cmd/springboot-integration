package com.kafka.consumer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KafkaListenerProperties {

    /**
     * 在侦听器容器中运行的线程数
     */
    private String concurrency;
    /**
     * listner负责ack，每调用一次，就立即commit
     */
    private String ackMode;
    /**
     * 在侦听器容器中运行的线程数
     */
    private String missingTopicsFatal;

}
