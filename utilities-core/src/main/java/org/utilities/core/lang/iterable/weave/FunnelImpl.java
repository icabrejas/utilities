package org.utilities.core.lang.iterable.weave;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.utilities.core.lang.iterable.observer.ObserverImpl;
import org.utilities.core.util.function.ConsumerPlus;

public class FunnelImpl<T> extends ObserverImpl<T> implements Funnel<T> {

	private Comparator<T> comparator;

	public FunnelImpl(Comparator<T> comparator) {
		super(ConsumerPlus.dummy());
		this.comparator = comparator;
	}

	@Override
	public int compare(T t1, T t2) {
		return comparator.compare(t1, t2);
	}

	@Override
	public FunnelImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public <R> FunnelImpl<T> start(Consumer<R> start, R r) {
		super.start(start, r);
		return this;
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
	public <U> FunnelImpl<T> next(BiConsumer<T, U> next, U u) {
		super.next(next, u);
		return this;
	}

	@Override
	public FunnelImpl<T> end(Runnable end) {
		super.end(end);
		return this;
	}

	@Override
	public <R> FunnelImpl<T> end(Consumer<R> end, R r) {
		super.end(end, r);
		return this;
	}

}
