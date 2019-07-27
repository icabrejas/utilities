package org.utilities.core.lang.iterable.tracker;

import java.io.Closeable;

import org.utilities.core.util.function.RunnablePlus;

public interface Tracker<T> {

	void onStart();

	void onEnd();

	// TODO move to utilities-io
	public static <T> TrackerImpl<T> closer(Closeable closeable) {
		return new TrackerImpl<T>().end(RunnablePlus.parseQuiet(closeable::close));
	}

}
