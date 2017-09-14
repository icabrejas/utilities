package org.utilities.core.lang.iterable.timeseries.filters;

import java.util.function.Predicate;

import org.utilities.core.lang.iterable.timeseries.Event;

public interface EventFilter<I> extends Predicate<Event<I>> {

	public static <I> EventFilter<I> newInstance(Predicate<Event<I>> filter) {
		return filter::test;
	}

	public static <I> EventFilter<I> and(EventFilter<I> a, EventFilter<I> b) {
		return a.and(b);
	}

	public static <I> EventFilter<I> or(EventFilter<I> a, EventFilter<I> b) {
		return a.or(b);
	}

	default EventFilter<I> and(EventFilter<I> filter) {
		return evt -> test(evt) && filter.test(evt);
	}

	default EventFilter<I> or(EventFilter<I> filter) {
		return evt -> test(evt) || filter.test(evt);
	}

	default EventFilter<I> negate() {
		return evt -> !test(evt);
	}

	public static <I> EventFilter<I> newInstance(Class<I> info) {
		return evt -> true;
	}

}
