package org.utilities.core.lang.iterable.timeseries.filters;

import org.utilities.core.lang.iterable.timeseries.Event;

public abstract class EventFilterComposed<I, V> implements EventFilter<I, V> {

	private EventFilter<I, V> left;
	private EventFilter<I, V> right;

	protected EventFilterComposed(EventFilter<I, V> left, EventFilter<I, V> right) {
		this.left = left;
		this.right = right;
	}

	public EventFilter<I, V> getLeft() {
		return left;
	}

	public EventFilter<I, V> getRight() {
		return right;
	}

	public static class And<I, V> extends EventFilterComposed<I, V> {

		public And(EventFilter<I, V> left, EventFilter<I, V> right) {
			super(left, right);
		}

		@Override
		public boolean test(Event<I, V> t) {
			return getLeft().test(t) && getRight().test(t);
		}

	}

	public static class Or<I, V> extends EventFilterComposed<I, V> {

		public Or(EventFilter<I, V> left, EventFilter<I, V> right) {
			super(left, right);
		}

		@Override
		public boolean test(Event<I, V> t) {
			return getLeft().test(t) || getRight().test(t);
		}

	}

}
