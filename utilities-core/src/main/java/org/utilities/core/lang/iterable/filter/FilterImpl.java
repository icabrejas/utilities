package org.utilities.core.lang.iterable.filter;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.tracker.TrackerImpl;

public class FilterImpl<T> extends TrackerImpl<T> implements Filter<T> {

	private Predicate<T> emit;

	public FilterImpl(Predicate<T> emit) {
		this.emit = emit;
	}

	@Override
	public boolean test(T t) {
		return emit.test(t);
	}

	@Override
	public FilterImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public <R> FilterImpl<T> start(Consumer<R> start, R r) {
		super.start(start, r);
		return this;
	}

	public FilterImpl<T> emit(Predicate<T> emit) {
		this.emit = emit;
		return this;
	}

	@Override
	public FilterImpl<T> end(Runnable end) {
		super.end(end);
		return this;
	}

	@Override
	public <R> FilterImpl<T> end(Consumer<R> end, R r) {
		super.end(end, r);
		return this;
	}

}
