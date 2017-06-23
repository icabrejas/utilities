package org.utilities.core.lang.iterable.timeseries.filters;

import java.util.function.Predicate;

import org.utilities.core.lang.iterable.timeseries.Event;

public interface EventFilter<I, V> extends Predicate<Event<I, V>> {

	public static <I, V> EventFilter<I, V> newInstance(Predicate<Event<I, V>> filter) {
		return filter::test;
	}

	public static <I, V> EventFilter<I, V> and(EventFilter<I, V> a, EventFilter<I, V> b) {
		return a.and(b);
	}

	public static <I, V> EventFilter<I, V> or(EventFilter<I, V> a, EventFilter<I, V> b) {
		return a.or(b);
	}

	default EventFilter<I, V> and(EventFilter<I, V> filter) {
		return evt -> test(evt) && filter.test(evt);
	}

	default EventFilter<I, V> or(EventFilter<I, V> filter) {
		return evt -> test(evt) || filter.test(evt);
	}

	default EventFilter<I, V> negate() {
		return evt -> !test(evt);
	}

	public static <I, V>EventFilter<I, V> newInstance(Class<I> info, Class<V> value) {
		return evt -> true;
	}
	

}
