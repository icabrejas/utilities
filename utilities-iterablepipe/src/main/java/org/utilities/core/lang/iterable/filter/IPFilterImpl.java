package org.utilities.core.lang.iterable.filter;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;

public class IPFilterImpl<T> extends IPTrackerImpl<T> implements IPFilter<T> {

	private BiPredicate<Integer, T> emit;

	public IPFilterImpl(BiPredicate<Integer, T> emit) {
		super();
		this.emit = emit;
	}

	public IPFilterImpl(Consumer<Integer> start, BiConsumer<Integer, T> next, Consumer<Integer> end,
			BiPredicate<Integer, T> emit) {
		super(start, next, end);
		this.emit = emit;
	}

	public IPFilterImpl(IPTracker<T> tracker, BiPredicate<Integer, T> emit) {
		super(tracker);
		this.emit = emit;
	}

	public IPFilterImpl(Predicate<T> emit) {
		super();
		this.emit = (serialNumber, t) -> emit.test(t);
	}

	public IPFilterImpl(Consumer<Integer> start, BiConsumer<Integer, T> next, Consumer<Integer> end,
			Predicate<T> emit) {
		super(start, next, end);
		this.emit = (serialNumber, t) -> emit.test(t);
	}

	public IPFilterImpl(IPTracker<T> tracker, Predicate<T> emit) {
		super(tracker);
		this.emit = (serialNumber, t) -> emit.test(t);
	}

	@Override
	public boolean emit(int serialNumber, T t) {
		return emit.test(serialNumber, t);
	}

	public IPFilterImpl<T> emit(BiPredicate<Integer, T> emit) {
		this.emit = emit;
		return this;
	}

}
