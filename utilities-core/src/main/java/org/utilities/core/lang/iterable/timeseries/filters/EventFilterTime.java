package org.utilities.core.lang.iterable.timeseries.filters;

import org.utilities.core.lang.iterable.timeseries.Event;
import org.utilities.core.time.Unixtime;

public class EventFilterTime {

	public static <I, V> EventFilter<I, V> isEquals(long unixtime) {
		return new Equals<>(unixtime);
	}

	public static <I, V> EventFilter<I, V> isEquals(int field, int value) {
		return new FieldEquals<>(field, value);
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isHigher(long unixtime) {
		return new Higher<>(unixtime);
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isHigherOrEquals(long unixtime) {
		EventFilter<I, V> higher = isHigher(unixtime);
		return higher.or(isEquals(unixtime));
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isLower(long unixtime) {
		return new Lower<I, V>(unixtime);
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isLowerOrEquals(long unixtime) {
		EventFilter<I, V> lower = isLower(unixtime);
		return lower.or(isEquals(unixtime));
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isBetween(long from, long to) {
		EventFilter<I, V> higher = isHigher(from);
		return higher.and(isLower(to));
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isBetweenOrEquals(long from, long to) {
		EventFilter<I, V> higher = isHigherOrEquals(from);
		return higher.and(isLowerOrEquals(to));
	}

	public static class Equals<I, V> implements EventFilter<I, V> {

		private Unixtime unixtime;

		private Equals(long unixtime) {
			this.unixtime = new Unixtime(1000 * unixtime);
		}

		@Override
		public boolean test(Event<I, V> evt) {
			return unixtime.equals(evt.getUnixtime());
		}

	}

	public static class Lower<I, V> implements EventFilter<I, V> {

		private Unixtime unixtime;

		private Lower(long unixtime) {
			this.unixtime = new Unixtime(1000 * unixtime);
		}

		@Override
		public boolean test(Event<I, V> evt) {
			return 0 < unixtime.compareTo(evt.getUnixtime());
		}

	}

	public static class Higher<I, V> implements EventFilter<I, V> {

		private Unixtime unixtime;

		private Higher(long unixtime) {
			this.unixtime = new Unixtime(1000 * unixtime);
		}

		@Override
		public boolean test(Event<I, V> evt) {
			return unixtime.compareTo(evt.getUnixtime()) < 0;
		}

	}

	public static class FieldEquals<I, V> implements EventFilter<I, V> {

		private int field;
		private int value;

		public FieldEquals(int field, int value) {
			this.field = field;
			this.value = value;
		}

		@Override
		public boolean test(Event<I, V> evt) {
			return value == evt.getUnixtime()
					.get(field);
		}

	}

}
