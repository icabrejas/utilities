package org.utilities.core.lang.iterable.batch;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;

public class IPBatchSemaphoreImpl<T> extends IPTrackerImpl<T> implements IPBatchSemaphore<T> {

	private BiPredicate<Integer, T> store;

	public IPBatchSemaphoreImpl(BiPredicate<Integer, T> store) {
		super();
		this.store = store;
	}

	public IPBatchSemaphoreImpl(Consumer<Integer> start, BiConsumer<Integer, T> next, Consumer<Integer> end,
			BiPredicate<Integer, T> store) {
		super(start, next, end);
		this.store = store;
	}

	public IPBatchSemaphoreImpl(IPTracker<T> tracker, BiPredicate<Integer, T> store) {
		super(tracker);
		this.store = store;
	}

	public IPBatchSemaphoreImpl(Predicate<T> store) {
		super();
		this.store = (serialNumber, t) -> store.test(t);
	}

	public IPBatchSemaphoreImpl(Consumer<Integer> start, BiConsumer<Integer, T> next, Consumer<Integer> end,
			Predicate<T> store) {
		super(start, next, end);
		this.store = (serialNumber, t) -> store.test(t);
	}

	public IPBatchSemaphoreImpl(IPTracker<T> tracker, Predicate<T> store) {
		super(tracker);
		this.store = (serialNumber, t) -> store.test(t);
	}

	@Override
	public boolean store(int serialNumber, T t) {
		return store.test(serialNumber, t);
	}

	public IPBatchSemaphoreImpl<T> store(BiPredicate<Integer, T> store) {
		this.store = store;
		return this;
	}

}
