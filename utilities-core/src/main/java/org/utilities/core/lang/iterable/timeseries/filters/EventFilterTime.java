package org.utilities.core.lang.iterable.timeseries.filters;

import java.util.function.Predicate;

import org.utilities.core.lang.iterable.timeseries.Event;
import org.utilities.core.time.Unixtime;
import org.utilities.core.util.function.PredicatePlus;

public class EventFilterTime<I> implements EventFilter<I> {

	private Predicate<Long> predicate;

	private EventFilterTime(Predicate<Long> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean test(Event<I> evt) {
		return predicate.test(evt.getTimeInMillis());
	}

	public EventFilterTime<I> and(EventFilterTime<I> filter) {
		return new EventFilterTime<>(PredicatePlus.and(this.predicate, filter.predicate));
	}

	@Override
	public EventFilterTime<I> negate() {
		return new EventFilterTime<>(this.predicate.negate());
	}

	public EventFilterTime<I> or(EventFilterTime<I> filter) {
		return new EventFilterTime<>(PredicatePlus.or(this.predicate, filter.predicate));
	}

	public static <I> EventFilterTime<I> isEquals(long milliseconds) {
		return new Equals<>(milliseconds);
	}

	public static <I> EventFilterTime<I> isEquals(int field, int value) {
		return new FieldEquals<>(field, value);
	}

	public static <I> EventFilterTime<I> isHigher(long milliseconds) {
		return new Higher<>(milliseconds);
	}

	public static <I> EventFilterTime<I> isHigherOrEquals(long milliseconds) {
		EventFilterTime<I> higher = EventFilterTime.isHigher(milliseconds);
		return higher.or(EventFilterTime.isEquals(milliseconds));
	}

	public static <I> EventFilterTime<I> isLower(long milliseconds) {
		return new Lower<I>(milliseconds);
	}

	public static <I> EventFilterTime<I> isLowerOrEquals(long milliseconds) {
		EventFilterTime<I> lower = EventFilterTime.isLower(milliseconds);
		return lower.or(EventFilterTime.isEquals(milliseconds));
	}

	public static <I> EventFilterTime<I> isBetween(long from, long to) {
		EventFilterTime<I> higher = EventFilterTime.isHigher(from);
		return higher.and(EventFilterTime.isLower(to));
	}

	public static <I> EventFilterTime<I> isBetweenOrEquals(long from, long to) {
		EventFilterTime<I> higher = EventFilterTime.isHigherOrEquals(from);
		return higher.and(EventFilterTime.isLowerOrEquals(to));
	}

	private static class Lower<I> extends EventFilterTime<I> {

		private Lower(long reference) {
			super(milliseconds -> Long.compare(milliseconds, reference) < 0);
		}

	}

	private static class Equals<I> extends EventFilterTime<I> {

		private Equals(long reference) {
			super(milliseconds -> Long.compare(milliseconds, reference) == 0);
		}

	}

	private static class Higher<I> extends EventFilterTime<I> {

		private Higher(long reference) {
			super(milliseconds -> 0 < Long.compare(milliseconds, reference));
		}

	}

	private static class FieldEquals<I> extends EventFilterTime<I> {

		public FieldEquals(int field, int value) {
			super(milliseconds -> value == new Unixtime(milliseconds).get(field));
		}

	}

}