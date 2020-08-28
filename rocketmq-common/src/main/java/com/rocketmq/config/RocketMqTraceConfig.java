package com.rocketmq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 监听配置信息
 */
@Component
public class RocketMqTraceConfig {
	public static String TRACE_TOPIC;
	public static String TRACE_TAG;
	
	@Value("${rocketmq.consumer.topic:#{null}}")
	public void setTraceTopic(String traceTopic) {
		TRACE_TOPIC = traceTopic;
	}
	
	@Value("${rocketmq.consumer.tag:#{null}}")
	public void setTraceTag(String traceTag) {
		TRACE_TAG = traceTag;
	}
	
}
