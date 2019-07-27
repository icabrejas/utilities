package org.utilities.core.lang.iterable.tracker;

import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;
import org.utilities.core.util.function.RunnablePlus;

public class TrackerImpl<T> implements Tracker<T> {

	private Runnable start = RunnablePlus.dummy();
	private Runnable end = RunnablePlus.dummy();

	@Override
	public void onStart() {
		start.run();
	}

	@Override
	public void onEnd() {
		end.run();
	}

	public TrackerImpl<T> start(Runnable start) {
		this.start = start;
		return this;
	}

	public <R> TrackerImpl<T> start(Consumer<R> start, R r) {
		return start(ConsumerPlus.parseRunnable(start, r));
	}

	public TrackerImpl<T> end(Runnable end) {
		this.end = end;
		return this;
	}

	public <R> TrackerImpl<T> end(Consumer<R> end, R r) {
		return end(ConsumerPlus.parseRunnable(end, r));
	}

}
