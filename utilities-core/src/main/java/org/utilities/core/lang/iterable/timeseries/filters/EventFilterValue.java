package org.utilities.core.lang.iterable.timeseries.filters;

import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.lang.iterable.timeseries.Event;

public abstract class EventFilterValue<I> implements EventFilter<I> {

	private String name;
	private DataValue value;

	protected EventFilterValue(String name, DataValue value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public DataValue getValue() {
		return value;
	}

	public static <I> EventFilter<I> isNull(String name) {
		return isEquals(name, null);
	}

	public static <I> EventFilter<I> isNotNull(String name) {
		EventFilter<I> filter = isNull(name);
		return filter.negate();
	}

	public static <I> EventFilter<I> isEquals(String name, DataValue value) {
		return new Equals<>(name, value);
	}

	public static <I> EventFilter<I> isNotEquals(String name, DataValue value) {
		EventFilter<I> filter = isEquals(name, value);
		return filter.negate();
	}

	public static <I> EventFilter<I> isHigher(String name, DataValue value) {
		return new Higher<>(name, value);
	}

	public static <I> EventFilter<I> isHigherOrEquals(String name, DataValue value) {
		return EventFilter.or(isHigher(name, value), isEquals(name, value));
	}

	public static <I> EventFilter<I> isLower(String name, DataValue value) {
		return new Lower<>(name, value);
	}

	public static <I> EventFilter<I> isLowerOrEquals(String name, DataValue value) {
		return EventFilter.or(isLower(name, value), isEquals(name, value));
	}

	public static <I> EventFilter<I> isBetween(String name, DataValue min, DataValue max) {
		return EventFilter.and(isHigher(name, min), isLower(name, max));
	}

	public static <I> EventFilter<I> isBetweenOrEquals(String name, DataValue min, DataValue max) {
		return EventFilter.and(isHigherOrEquals(name, min), isLowerOrEquals(name, max));
	}

	public static class Equals<I> extends EventFilterValue<I> {

		public Equals(String name, DataValue value) {
			super(name, value);
		}

		@Override
		public boolean test(Event<I> evt) {
			String name = getName();
			DataValue value = getValue();
			return value == null ? evt.get(name) == null : value.equals(evt.get(name));
		}

	}

	public static class Lower<I> extends EventFilterValue<I> {

		public Lower(String name, DataValue value) {
			super(name, value);
		}

		@Override
		public boolean test(Event<I> evt) {
			String name = getName();
			DataValue value = getValue();
			return evt.get(name) != null && 0 < value.compareTo(evt.get(name));
		}

	}

	public static class Higher<I> extends EventFilterValue<I> {

		public Higher(String name, DataValue value) {
			super(name, value);
		}

		@Override
		public boolean test(Event<I> evt) {
			String name = getName();
			DataValue value = getValue();
			return evt.get(name) != null && value.compareTo(evt.get(name)) < 0;
		}

	}

}
