package org.utilities.core.lang.iterable.batch;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.utilities.core.lang.iterable.tracker.TrackerImpl;

public class SemaphoreImpl<T> extends TrackerImpl<T> implements Semaphore<T> {

	private BiPredicate<T, T> store;

	public SemaphoreImpl(BiPredicate<T, T> store) {
		this.store = store;
	}

	@Override
	public boolean store(T prev, T next) {
		return store.test(prev, next);
	}

	@Override
	public SemaphoreImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public <R> SemaphoreImpl<T> start(Consumer<R> start, R r) {
		super.start(start, r);
		return this;
	}

	public SemaphoreImpl<T> store(BiPredicate<T, T> store) {
		this.store = store;
		return this;
	}

	@Override
	public SemaphoreImpl<T> end(Runnable end) {
		super.end(end);
		return this;
	}

	@Override
	public <R> SemaphoreImpl<T> end(Consumer<R> end, R r) {
		super.end(end, r);
		return this;
	}

}
