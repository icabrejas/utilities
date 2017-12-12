package org.utilities.core.lang.iterable.limit;

import org.utilities.core.lang.iterable.tracker.Tracker;

public interface StopCriteria<T> extends Tracker<T> {

	boolean stop(T t);

}
