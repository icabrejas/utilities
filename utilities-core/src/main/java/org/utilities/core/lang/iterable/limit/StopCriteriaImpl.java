package org.utilities.core.lang.iterable.limit;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.tracker.TrackerImpl;

public class StopCriteriaImpl<T> extends TrackerImpl<T> implements StopCriteria<T> {

	private Predicate<T> stop;

	public StopCriteriaImpl(Predicate<T> stop) {
		this.stop = stop;
	}

	@Override
	public boolean stop(T t) {
		return stop.test(t);
	}

	@Override
	public StopCriteriaImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public <R> StopCriteriaImpl<T> start(Consumer<R> start, R r) {
		super.start(start, r);
		return this;
	}

	public StopCriteriaImpl<T> stop(Predicate<T> stop) {
		this.stop = stop;
		return this;
	}

	@Override
	public StopCriteriaImpl<T> end(Runnable end) {
		super.end(end);
		return this;
	}

	@Override
	public <R> StopCriteriaImpl<T> end(Consumer<R> end, R r) {
		super.end(end, r);
		return this;
	}

}
