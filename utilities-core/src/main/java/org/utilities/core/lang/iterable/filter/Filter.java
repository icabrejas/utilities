package org.utilities.core.lang.iterable.filter;

import org.utilities.core.lang.iterable.tracker.Tracker;

public interface Filter<T> extends Tracker<T> {

	boolean emit(T t);
	
}
