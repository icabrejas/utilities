package org.utilities.core.lang.iterable.timeseries.filters;

import org.utilities.core.lang.iterable.timeseries.Event;

public abstract class EventFilterComposed<I> implements EventFilter<I> {

	private EventFilter<I> left;
	private EventFilter<I> right;

	protected EventFilterComposed(EventFilter<I> left, EventFilter<I> right) {
		this.left = left;
		this.right = right;
	}

	public EventFilter<I> getLeft() {
		return left;
	}

	public EventFilter<I> getRight() {
		return right;
	}

	public static class And<I> extends EventFilterComposed<I> {

		public And(EventFilter<I> left, EventFilter<I> right) {
			super(left, right);
		}

		@Override
		public boolean test(Event<I> t) {
			return getLeft().test(t) && getRight().test(t);
		}

	}

	public static class Or<I> extends EventFilterComposed<I> {

		public Or(EventFilter<I> left, EventFilter<I> right) {
			super(left, right);
		}

		@Override
		public boolean test(Event<I> t) {
			return getLeft().test(t) || getRight().test(t);
		}

	}

}
