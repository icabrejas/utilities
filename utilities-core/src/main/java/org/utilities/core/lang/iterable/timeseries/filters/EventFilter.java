package org.utilities.core.lang.iterable.timeseries.filters;

import java.util.function.Predicate;

import org.utilities.core.lang.iterable.timeseries.Event;
import org.utilities.core.util.function.PredicatePlus;

public interface EventFilter<I> extends PredicatePlus<Event<I>> {

	public static <I> EventFilter<I> newInstance(Class<I> info) {
		return evt -> true;
	}

	public static <I> EventFilter<I> newInstance(Predicate<Event<I>> filter) {
		return filter::test;
	}

	public static <I> EventFilter<I> or(EventFilter<I> a, EventFilter<I> b) {
		return PredicatePlus.or(a, b)::test;
	}

	@Override
	default EventFilter<I> and(Predicate<? super Event<I>> filter) {
		return PredicatePlus.super.and(filter)::test;
	}

	@Override
	default EventFilter<I> negate() {
		return PredicatePlus.super.negate()::test;
	}

	@Override
	default EventFilter<I> or(Predicate<? super Event<I>> filter) {
		return PredicatePlus.super.or(filter)::test;
	}

}
