package org.utilities.core.lang.iterable.filter;

import java.util.function.Predicate;

import org.utilities.core.lang.iterable.tracker.Tracker;
import org.utilities.core.util.function.PredicatePlus;
import org.utilities.core.util.lambda.LambdaInt;

public interface Filter<T> extends Tracker<T>, PredicatePlus<T> {

	@Override
	default FilterImpl<T> and(Predicate<? super T> other) {
		return new FilterImpl<T>(PredicatePlus.super.and(other))//
				.start(this::onStart)
				.end(this::onEnd);
	}

	@Override
	default FilterImpl<T> negate() {
		return new FilterImpl<T>(PredicatePlus.super.negate())//
				.start(this::onStart)
				.end(this::onEnd);
	}

	@Override
	default FilterImpl<T> or(Predicate<? super T> other) {
		return new FilterImpl<T>(PredicatePlus.super.or(other))//
				.start(this::onStart)
				.end(this::onEnd);
	}

	public static <T> FilterImpl<T> skip(int times) {
		LambdaInt counter = new LambdaInt();
		return new FilterImpl<T>(t -> times < counter.increment(1))//
				.start(() -> counter.set(0));
	}

	public static <T> FilterImpl<T> subsample(int by) {
		LambdaInt counter = new LambdaInt();
		return new FilterImpl<T>(t -> counter.increment(1) % by == 0)//
				.start(() -> counter.set(-1));
	}

}
