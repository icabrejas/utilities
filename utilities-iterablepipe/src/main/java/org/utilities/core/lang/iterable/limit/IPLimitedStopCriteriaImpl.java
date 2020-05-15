package org.utilities.core.lang.iterable.limit;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;

public class IPLimitedStopCriteriaImpl<T> extends IPTrackerImpl<T> implements IPLimitedStopCriteria<T> {

	private BiPredicate<Integer, T> stop;

	public IPLimitedStopCriteriaImpl(BiPredicate<Integer, T> stop) {
		super();
		this.stop = stop;
	}

	public IPLimitedStopCriteriaImpl(Consumer<Integer> start, BiConsumer<Integer, T> next, Consumer<Integer> end,
			BiPredicate<Integer, T> stop) {
		super(start, next, end);
		this.stop = stop;
	}

	public IPLimitedStopCriteriaImpl(IPTracker<T> tracker, BiPredicate<Integer, T> stop) {
		super(tracker);
		this.stop = stop;
	}

	public IPLimitedStopCriteriaImpl(Predicate<T> stop) {
		super();
		this.stop = (serialNumber, t) -> stop.test(t);
	}

	public IPLimitedStopCriteriaImpl(Consumer<Integer> start, BiConsumer<Integer, T> next, Consumer<Integer> end,
			Predicate<T> stop) {
		super(start, next, end);
		this.stop = (serialNumber, t) -> stop.test(t);
	}

	public IPLimitedStopCriteriaImpl(IPTracker<T> tracker, Predicate<T> stop) {
		super(tracker);
		this.stop = (serialNumber, t) -> stop.test(t);
	}

	@Override
	public boolean stop(int serialNumber, T t) {
		return stop.test(serialNumber, t);
	}

	public IPLimitedStopCriteriaImpl<T> stop(BiPredicate<Integer, T> stop) {
		this.stop = stop;
		return this;
	}

}
