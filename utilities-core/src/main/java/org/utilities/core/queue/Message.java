package org.utilities.core.queue;

public class Message<M> {

	private String topic;
	private long submitTime;
	private M message;

	public Message(String topic, M message) {
		this.submitTime = System.currentTimeMillis();
		this.topic = topic;
		this.message = message;
	}

	public String getTopic() {
		return topic;
	}

	public long getSubmitTime() {
		return submitTime;
	}

	public M getMessage() {
		return message;
	}

}
