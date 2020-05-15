package org.utilities.core.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.utilities.core.UtilitiesTime;

public class TrackedFuture<V> implements Future<V> {

	private Future<V> future;
	private TrackedCallable<V> trackedCallable;
	private long submitTime;

	public TrackedFuture(Future<V> future, TrackedCallable<V> trackedCallable) {
		this.future = future;
		this.submitTime = System.currentTimeMillis();
		this.trackedCallable = trackedCallable;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return future.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return future.isCancelled();
	}

	@Override
	public boolean isDone() {
		return future.isDone();
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		return future.get();
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return future.get(timeout, unit);
	}

	public long getSubmitTime() {
		return submitTime;
	}

	public Long getCallTime() {
		return trackedCallable.getCallTime();
	}

	public Long getDoneTime() {
		return trackedCallable.getDoneTime();
	}

	@Override
	public String toString() {
		Long callTime = trackedCallable.getCallTime();
		Long doneTime = trackedCallable.getDoneTime();
		Long submitTime = callTime != null ? Math.min(callTime, this.submitTime) : this.submitTime;
		StringBuilder builder = new StringBuilder()//
				.append("TrackedFuture [")
				.append(isDone() ? "done" : "waiting")
				.append(", submit=" + UtilitiesTime.formatMillis(submitTime));
		if (callTime != null) {
			builder = builder.append(", call=" + UtilitiesTime.formatMillis(callTime) + " ("
					+ (UtilitiesTime.formatMillis(callTime - submitTime)) + ")");
		} else {
			builder = builder.append(", call=???");
		}
		if (callTime != null && doneTime != null) {
			builder = builder.append(", done=" + UtilitiesTime.formatMillis(doneTime) + " ("
					+ (UtilitiesTime.formatMillis(doneTime - callTime)) + ")");
		} else {
			builder = builder.append(", done=???");
		}
		return builder.append("]")
				.toString();
	}

}
