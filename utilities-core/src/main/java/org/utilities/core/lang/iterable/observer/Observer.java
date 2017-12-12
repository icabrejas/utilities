package org.utilities.core.lang.iterable.observer;

import org.utilities.core.lang.iterable.tracker.Tracker;

public interface Observer<T> extends Tracker<T> {

	void onNext(T next);

}
