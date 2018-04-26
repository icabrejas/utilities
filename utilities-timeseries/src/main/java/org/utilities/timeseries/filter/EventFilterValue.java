package org.utilities.timeseries.filter;

import java.util.function.Predicate;

import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.util.function.PredicatePlus;
import org.utilities.timeseries.Event;

public class EventFilterValue<I> implements EventFilter<I> {

	private String label;
	private Predicate<DataValue> predicate;

	private EventFilterValue(String label, Predicate<DataValue> predicate) {
		this.label = label;
		this.predicate = predicate;
	}

	@Override
	public boolean test(Event<I> evt) {
		return predicate.test(evt.get(label));
	}

	@Override
	public EventFilterValue<I> negate() {
		return new EventFilterValue<>(label, this.predicate.negate());
	}

	public static <I> EventFilterValue<I> and(String label, Predicate<DataValue> a, Predicate<DataValue> b) {
		return new EventFilterValue<>(label, PredicatePlus.and(a, b));
	}

	public static <I> EventFilterValue<I> negate(EventFilterValue<I> filter) {
		return filter.negate();
	}

	public static <I> EventFilterValue<I> or(String label, Predicate<DataValue> a, Predicate<DataValue> b) {
		return new EventFilterValue<>(label, PredicatePlus.or(a, b));
	}

	public static <I> EventFilterValue<I> isNull(String name) {
		return new Null<>(name);
	}

	public static <I> EventFilterValue<I> isNotNull(String name) {
		return negate(new Null<>(name));
	}

	public static <I> EventFilterValue<I> isEquals(String name, DataValue value) {
		return new Equals<>(name, value);
	}

	public static <I> EventFilterValue<I> isNotEquals(String name, DataValue value) {
		return negate(isEquals(name, value));
	}

	public static <I> EventFilterValue<I> isHigher(String name, DataValue value) {
		return new Higher<>(name, value);
	}

	public static <I> EventFilterValue<I> isHigherOrEquals(String name, DataValue value) {
		return negate(isLower(name, value));
	}

	public static <I> EventFilterValue<I> isLower(String name, DataValue value) {
		return new Lower<>(name, value);
	}

	public static <I> EventFilter<I> isLowerOrEquals(String name, DataValue value) {
		return negate(isHigher(name, value));
	}

	public static <I> EventFilterValue<I> isBetween(String name, DataValue min, DataValue max) {
		Predicate<DataValue> left = Higher.predicate(min);
		Predicate<DataValue> right = Lower.predicate(max);
		return and(name, left, right);
	}

	public static <I> EventFilter<I> isBetweenOrEquals(String name, DataValue min, DataValue max) {
		Predicate<DataValue> left = PredicatePlus.or(Higher.predicate(min), (Equals.predicate(min)));
		Predicate<DataValue> right = PredicatePlus.or(Lower.predicate(max), (Equals.predicate(max)));
		return and(name, left, right);
	}

	public static class Lower<I> extends EventFilterValue<I> {

		public Lower(String name, DataValue reference) {
			super(name, Lower.predicate(reference));
		}

		public static Predicate<DataValue> predicate(DataValue reference) {
			return value -> 0 < reference.compareTo(value);
		}

	}

	public static class Equals<I> extends EventFilterValue<I> {

		public Equals(String name, DataValue reference) {
			super(name, Equals.predicate(reference));
		}

		public static Predicate<DataValue> predicate(DataValue reference) {
			return value -> reference.equals(value);
		}

	}

	public static class Higher<I> extends EventFilterValue<I> {

		public Higher(String name, DataValue reference) {
			super(name, Higher.predicate(reference));
		}

		public static Predicate<DataValue> predicate(DataValue reference) {
			return value -> reference.compareTo(value) < 0;
		}

	}

	public static class Null<I> extends EventFilterValue<I> {

		public Null(String name) {
			super(name, Null.predicate());
		}

		public static Predicate<DataValue> predicate() {
			return PredicatePlus.isNull();
		}

	}

}
