package org.utilities.core.lang.iterable.observer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.utilities.core.lang.iterable.tracker.TrackerImpl;
import org.utilities.core.util.function.BiConsumerPlus;

public class ObserverImpl<T> extends TrackerImpl<T> implements Observer<T> {

	private Consumer<T> next;

	public ObserverImpl(Consumer<T> next) {
		this.next = next;
	}

	@Override
	public void onNext(T next) {
		this.next.accept(next);
	}

	@Override
	public ObserverImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public <R> ObserverImpl<T> start(Consumer<R> start, R r) {
		super.start(start, r);
		return this;
	}

	public ObserverImpl<T> next(Consumer<T> next) {
		this.next = next;
		return this;
	}

	public <U> ObserverImpl<T> next(BiConsumer<T, U> next, U u) {
		return next(BiConsumerPlus.parseConsumer(next, u));
	}

	@Override
	public ObserverImpl<T> end(Runnable end) {
		super.end(end);
		return this;
	}

	@Override
	public <R> ObserverImpl<T> end(Consumer<R> end, R r) {
		super.end(end, r);
		return this;
	}

}
