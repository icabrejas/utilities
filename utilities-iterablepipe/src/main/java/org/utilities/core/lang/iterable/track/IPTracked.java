package org.utilities.core.lang.iterable.track;

import org.utilities.core.lang.iterable.IterablePipe;

public class IPTracked<T> implements IterablePipe<T> {

	private int serialNumber;
	private Iterable<T> it;
	private IPTracker<T> tracker;

	public IPTracked(Iterable<T> it, IPTracker<T> tracker) {
		this.it = it;
		this.tracker = tracker;
	}

	@Override
	public ItTracked<T> iterator() {
		return new ItTracked<>(serialNumber++, it.iterator(), tracker);
	}

}
