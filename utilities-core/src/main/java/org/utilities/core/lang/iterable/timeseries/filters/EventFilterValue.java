package org.utilities.core.lang.iterable.timeseries.filters;

import org.utilities.core.lang.iterable.timeseries.Event;

public abstract class EventFilterValue<I, V> implements EventFilter<I, V> {

	private String label;
	private V value;

	protected EventFilterValue(String label, V value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public V getValue() {
		return value;
	}

	public static <I, V> EventFilter<I, V> isNull(String label) {
		return isEquals(label, null);
	}

	public static <I, V> EventFilter<I, V> isNotNull(String label) {
		EventFilter<I, V> filter = isNull(label);
		return filter.negate();
	}

	public static <I, V> EventFilter<I, V> isEquals(String label, V value) {
		return new Equals<>(label, value);
	}

	public static <I, V> EventFilter<I, V> isNotEquals(String label, V value) {
		EventFilter<I, V> filter = isEquals(label, value);
		return filter.negate();
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isHigher(String label, V value) {
		return new Higher<>(label, value);
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isHigherOrEquals(String label, V value) {
		return EventFilter.or(isHigher(label, value), isEquals(label, value));
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isLower(String label, V value) {
		return new Lower<>(label, value);
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isLowerOrEquals(String label, V value) {
		return EventFilter.or(isLower(label, value), isEquals(label, value));
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isBetween(String label, V min, V max) {
		return EventFilter.and(isHigher(label, min), isLower(label, max));
	}

	public static <I, V extends Comparable<V>> EventFilter<I, V> isBetweenOrEquals(String label, V min, V max) {
		return EventFilter.and(isHigherOrEquals(label, min), isLowerOrEquals(label, max));
	}

	public static class Equals<I, V> extends EventFilterValue<I, V> {

		public Equals(String label, V value) {
			super(label, value);
		}

		@Override
		public boolean test(Event<I, V> evt) {
			String label = getLabel();
			V value = getValue();
			return value == null ? evt.get(label) == null : value.equals(evt.get(label));
		}

	}

	public static class Lower<I, V extends Comparable<V>> extends EventFilterValue<I, V> {

		public Lower(String label, V value) {
			super(label, value);
		}

		@Override
		public boolean test(Event<I, V> evt) {
			String label = getLabel();
			V value = getValue();
			return evt.get(label) != null && 0 < value.compareTo(evt.get(label));
		}

	}

	public static class Higher<I, V extends Comparable<V>> extends EventFilterValue<I, V> {

		public Higher(String label, V value) {
			super(label, value);
		}

		@Override
		public boolean test(Event<I, V> evt) {
			String label = getLabel();
			V value = getValue();
			return evt.get(label) != null && value.compareTo(evt.get(label)) < 0;
		}

	}

}
