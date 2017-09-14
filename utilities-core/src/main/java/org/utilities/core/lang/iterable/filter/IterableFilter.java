package org.utilities.core.lang.iterable.filter;

import java.util.function.Predicate;

public interface IterableFilter<T> extends Predicate<T> {

	public static <T> IterableFilter<T> newInstance() {
		return newInstance(evt -> true);
	}

	public static <T> IterableFilter<T> newInstance(Predicate<T> filter) {
		return filter::test;
	}

	public static <T> IterableFilter<T> and(IterableFilter<T> a, IterableFilter<T> b) {
		return a.and(b);
	}

	public static <T> IterableFilter<T> or(IterableFilter<T> a, IterableFilter<T> b) {
		return a.or(b);
	}

	@Override
	default IterableFilter<T> or(Predicate<? super T> other) {
		return evt -> test(evt) || other.test(evt);
	}

	@Override
	default IterableFilter<T> and(Predicate<? super T> filter) {
		return evt -> test(evt) && filter.test(evt);
	}

	@Override
	default IterableFilter<T> negate() {
		return newInstance(this.negate());
	}

}
