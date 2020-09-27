package com.kafka.consumer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KafkaConsumerProperties {

    /**
     * topic
     */
    private String topic;
    /**
     * 标识此使用者所属的使用者组的唯一字符串
     */
    private String groupId;
    /**
     * 自动提交的时间间隔 在spring boot 2.X 版本中这里采用的是值的类型为Duration 需要符合特定的格式，如1S,1M,2H,5D
     */
    private String autoCommitInterval;
    /**
     * # 该属性指定了消费者在读取一个没有偏移量的分区或者偏移量无效的情况下该作何处理：
     * # latest（默认值）在偏移量无效的情况下，消费者将从最新的记录开始读取数据（在消费者启动之后生成的记录）
     * # earliest ：在偏移量无效的情况下，消费者将从起始位置读取分区的记录
     */
    private String autoOffsetReset;
    /**
     * 是否自动提交偏移量，默认值是true,为了避免出现重复数据和数据丢失，可以把它设置为false,然后手动提交偏移量
     */
    private String enableAutoCommit;
    /**
     * Maximum number of records returned in a single call to poll().
     */
    private Integer maxPollRecords;
    /**
     * 键的反序列化方式
     */
    private String keyDeserializer;
    /**
     * 值的序列化方式
     */
    private String valueDeserializer;

}
