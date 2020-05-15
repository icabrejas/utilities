package org.utilities.core.lang.iterable.track;

import java.util.Iterator;

public class ItTracked<T> implements Iterator<T> {

	private Iterator<T> it;
	private IPTrackerNotifier<T> notifier;

	public ItTracked(int serialNumber, Iterator<T> it, IPTracker<T> tracker) {
		this.it = it;
		this.notifier = new IPTrackerNotifier<T>(serialNumber, tracker);
	}

	@Override
	public boolean hasNext() {
		notifier.notifyStart();
		boolean isEmpty;
		if (isEmpty = !it.hasNext()) {
			notifier.notifyEnd();
		}
		return !isEmpty;
	}

	@Override
	public T next() {
		notifier.notifyStart();
		T next = it.next();
		notifier.notifyNext(next);
		return next;
	}

}
