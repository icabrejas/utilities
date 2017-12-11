package org.utilities.core.lang.iterable.filter;

import java.util.function.Predicate;

import org.utilities.core.lang.iterable.tracker.TrackerImpl;
import org.utilities.core.util.lambda.LambdaInt;

public class FilterImpl<T> extends TrackerImpl<T> implements Filter<T> {

	private Predicate<T> emit;

	@Override
	public FilterImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public boolean emit(T t) {
		return emit.test(t);
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

	public static <T> FilterImpl<T> skip(int times) {
		LambdaInt counter = new LambdaInt();
		return new FilterImpl<T>().start(() -> counter.set(0))
				.emit((T t) -> times < counter.add(1));
	}

	public static <T> FilterImpl<T> subsample(int by) {
		LambdaInt counter = new LambdaInt();
		return new FilterImpl<T>().start(() -> counter.set(-1))
				.emit((T t) -> counter.add(1) % by == 0);
	}

}
