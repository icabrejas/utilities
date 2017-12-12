package org.utilities.core.lang.iterable.tracker;

import java.io.Closeable;

import org.utilities.core.util.function.RunnablePlus;

public class TrackerImpl<T> implements Tracker<T> {

	private Runnable start = RunnablePlus.dummy();
	private Runnable end = RunnablePlus.dummy();

	@Override
	public void onStart() {
		start.run();
	}

	public TrackerImpl<T> start(Runnable start) {
		this.start = start;
		return this;
	}

	@Override
	public void onEnd() {
		end.run();
	}

	public TrackerImpl<T> end(Runnable end) {
		this.end = end;
		return this;
	}

	// TODO move to utilities-io
	public static <T> TrackerImpl<T> closer(Closeable closeable) {
		return new TrackerImpl<T>().end(RunnablePlus.parseQuiet(closeable::close));
	}

}
