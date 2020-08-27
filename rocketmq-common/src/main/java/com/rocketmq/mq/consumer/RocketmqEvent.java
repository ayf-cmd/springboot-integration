package com.rocketmq.mq.consumer;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class RocketmqEvent extends ApplicationEvent {
	@Getter
	@Setter
	private DefaultMQPushConsumer consumer;
	@Getter
	@Setter
	private List<MessageExt> msgs;

	public RocketmqEvent(List<MessageExt> msgs, DefaultMQPushConsumer consumer) {
		super(msgs);
		this.consumer = consumer;
		this.setMsgs(msgs);
	}

	public String getMsg(int idx) {
		return new String(getMsgs().get(idx).getBody());
	}

	public MessageExt getMessageExt(int idx) {
		return getMsgs().get(idx);
	}

	public String getTopic(int idx) {
		return getMsgs().get(idx).getTopic();
	}

	public String getTag(int idx) {
		return getMsgs().get(idx).getTags();
	}

	public byte[] getBody(int idx) {
		return getMsgs().get(idx).getBody();
	}

	public String getKeys(int idx) {
		return getMsgs().get(idx).getKeys();
	}
	
}
