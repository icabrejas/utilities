package org.utilities.core.lang.iterable.map;

import org.utilities.core.lang.iterable.tracker.Tracker;

public interface Mapper<T, R> extends Tracker<T> {

	R map(T t);
	
}
