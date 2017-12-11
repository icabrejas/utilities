package org.utilities.core.lang.iterable.weave;

import java.util.Comparator;
import java.util.function.Consumer;

import org.utilities.core.lang.iterable.observer.ObserverImpl;

public class FunnelImpl<T> extends ObserverImpl<T> implements Funnel<T> {

	private Comparator<T> comparator;

	public FunnelImpl(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public FunnelImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public int compare(T t1, T t2) {
		return comparator.compare(t1, t2);
	}

	public FunnelImpl<T> compare(Comparator<T> comparator) {
		this.comparator = comparator;
		return this;
	}

	@Override
	public FunnelImpl<T> next(Consumer<T> next) {
		super.next(next);
		return this;
	}

	@Override
	public FunnelImpl<T> end(Runnable end) {
		super.end(end);
		return this;
	}

}
