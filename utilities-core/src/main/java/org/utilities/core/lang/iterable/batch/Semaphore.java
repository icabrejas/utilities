package org.utilities.core.lang.iterable.batch;

import org.utilities.core.lang.iterable.tracker.Tracker;

public interface Semaphore<T> extends Tracker<T> {

	boolean store(T prev, T next);

}
