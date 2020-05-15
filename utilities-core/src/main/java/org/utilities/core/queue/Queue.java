package org.utilities.core.queue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import org.utilities.core.UtilitiesConcurrent;
import org.utilities.core.util.concurrent.Interval;

public class Queue<M> {

	private ConcurrentHashMap<String, ConcurrentLinkedQueue<Consumer<M>>> consumers = new ConcurrentHashMap<>();
	private ConcurrentLinkedQueue<Message<M>> messages = new ConcurrentLinkedQueue<>();
	private ExpirationCriteria expirationCriteria = new ExpirationCriteria();

	public Queue<M> expirationTime(Long expiration) {
		this.expirationCriteria.expiration(expiration);
		return this;
	}

	public Queue<M> expirationSize(Integer size) {
		this.expirationCriteria.size(size);
		return this;
	}

	public void append(String topic, M message) {
		Message<M> msg = new Message<M>(topic, message);
		messages.add(msg);
		for (Consumer<M> consumer : getConsumers(topic)) {
			consumer.accept(message);
		}
		System.out.println("queue:" + messages.size());
	}

	private ConcurrentLinkedQueue<Consumer<M>> getConsumers(String topic) {
		ConcurrentLinkedQueue<Consumer<M>> consumers = this.consumers.get(topic);
		if (consumers == null) {
			consumers = new ConcurrentLinkedQueue<>();
			this.consumers.put(topic, consumers);
		}
		return consumers;
	}

	public void subscribe(String topic, Consumer<M> consumer) {
		getConsumers(topic).add(consumer);
		for (Message<M> message : messages) {
			if(topic.equals(message.getTopic())) {
				consumer.accept(message.getMessage());
			}
		}
	}

	public void cancel(String topic, Consumer<M> consumer) {
		consumers.remove(consumer);
	}

	private class ExpirationCriteria implements Runnable {

		private Interval interval = UtilitiesConcurrent.setInterval(this);
		private Long expiration;
		private Integer size;

		public ExpirationCriteria expiration(Long expiration) {
			this.expiration = expiration;
			checkProcess();
			return this;
		}

		public ExpirationCriteria size(Integer size) {
			this.size = size;
			checkProcess();
			return this;
		}

		private void checkProcess() {
			if (expiration == null && size == null) {
				interval.stop();
			} else {
				interval.start();
			}
		}

		@Override
		public void run() {
			if (expiration != null) {
				Message<M> message = messages.peek();
				while (message != null && (expiration < System.currentTimeMillis() - message.getSubmitTime())) {
					messages.poll();
					message = messages.peek();
				}
			}
			if (size != null) {
				while (size <= messages.size()) {
					messages.poll();
				}
			}
		}

	}

}
