package org.utilities.core.util.concurrent;

import java.util.concurrent.Callable;

public class TrackedCallable<V> implements Callable<V> {

	private Callable<V> callable;
	private Long callTime;
	private Long doneTime;

	public TrackedCallable(Callable<V> callable) {
		this.callable = callable;
	}

	@Override
	public V call() throws Exception {
		callTime = System.currentTimeMillis();
		V value = callable.call();
		doneTime = System.currentTimeMillis();
		return value;
	}

	public Long getCallTime() {
		return callTime;
	}

	public Long getDoneTime() {
		return doneTime;
	}

}
