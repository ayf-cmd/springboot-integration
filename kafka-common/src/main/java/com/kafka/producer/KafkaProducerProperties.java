package com.kafka.producer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KafkaProducerProperties {

    /**
     * topic
     */
    private String topic;
    /**
     * 发生错误后，消息重发的次数
     */
    private String retries;
    /**
     * # acks=0 ： 生产者在成功写入消息之前不会等待任何来自服务器的响应。
     * # acks=1 ： 只要集群的首领节点收到消息，生产者就会收到一个来自服务器成功响应。
     * # acks=all ：只有当所有参与复制的节点全部收到消息时，生产者才会收到一个来自服务器的成功响应。
     */
    private String acks;
    /**
     * 当有多个消息需要被发送到同一个分区时，生产者会把它们放在同一个批次里。该参数指定了一个批次可以使用的内存大小，按照字节数计算。
     */
    private String batchSize;
    /**
     * 设置生产者内存缓冲区的大小。
     */
    private String bufferMoney;
    /**
     * 请求的最大大小（字节）。此设置将限制生产者在单个请求中发送的记录批数，以避免发送大量请求。
     * 这也有效地限制了最大记录批大小。请注意，服务器有自己的记录批大小上限，这可能与此不同。
     */
    private String maxRequestSize;
    /**
     * 键的序列化方式
     */
    private String keySerializer;
    /**
     * 值的序列化方式
     */
    private String valueSerializer;

}
