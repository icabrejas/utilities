package org.utilities.core.lang.iterable.timeseries.filters;

import org.utilities.core.lang.iterable.filter.IterableFilter;
import org.utilities.core.lang.iterable.timeseries.Event;
import org.utilities.core.time.Unixtime;

public class EventFilterTime {

	public static <I> IterableFilter<Event<I>> isEquals(long unixtime) {
		return new Equals<>(unixtime);
	}

	public static <I> IterableFilter<Event<I>> isEquals(int field, int value) {
		return new FieldEquals<>(field, value);
	}

	public static <I> IterableFilter<Event<I>> isHigher(long unixtime) {
		return new Higher<>(unixtime);
	}

	public static <I> IterableFilter<Event<I>> isHigherOrEquals(long unixtime) {
		IterableFilter<Event<I>> higher = isHigher(unixtime);
		return higher.or(isEquals(unixtime));
	}

	public static <I> IterableFilter<Event<I>> isLower(long unixtime) {
		return new Lower<I>(unixtime);
	}

	public static <I> IterableFilter<Event<I>> isLowerOrEquals(long unixtime) {
		IterableFilter<Event<I>> lower = isLower(unixtime);
		return lower.or(isEquals(unixtime));
	}

	public static <I> IterableFilter<Event<I>> isBetween(long from, long to) {
		IterableFilter<Event<I>> higher = isHigher(from);
		return higher.and(isLower(to));
	}

	public static <I> IterableFilter<Event<I>> isBetweenOrEquals(long from, long to) {
		IterableFilter<Event<I>> higher = isHigherOrEquals(from);
		return higher.and(isLowerOrEquals(to));
	}

	public static class Equals<I> implements IterableFilter<Event<I>> {

		private Unixtime unixtime;

		private Equals(long unixtime) {
			this.unixtime = new Unixtime(1000 * unixtime);
		}

		@Override
		public boolean test(Event<I> evt) {
			return unixtime.equals(evt.getUnixtime());
		}

	}

	public static class Lower<I> implements IterableFilter<Event<I>> {

		private Unixtime unixtime;

		private Lower(long unixtime) {
			this.unixtime = new Unixtime(1000 * unixtime);
		}

		@Override
		public boolean test(Event<I> evt) {
			return 0 < unixtime.compareTo(evt.getUnixtime());
		}

	}

	public static class Higher<I> implements IterableFilter<Event<I>> {

		private Unixtime unixtime;

		private Higher(long unixtime) {
			this.unixtime = new Unixtime(1000 * unixtime);
		}

		@Override
		public boolean test(Event<I> evt) {
			return unixtime.compareTo(evt.getUnixtime()) < 0;
		}

	}

	public static class FieldEquals<I> implements IterableFilter<Event<I>> {

		private int field;
		private int value;

		public FieldEquals(int field, int value) {
			this.field = field;
			this.value = value;
		}

		@Override
		public boolean test(Event<I> evt) {
			return value == evt.getUnixtime()
					.get(field);
		}

	}

}