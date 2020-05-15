package org.utilities.core.lang.iterable.filter;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.track.ItTracked;

public class ItFiltered<T> extends ItTracked<T> {

	private int serialNumber;
	private IPFilter<T> filter;
	private T next;

	public ItFiltered(int serialNumber, Iterator<T> it, IPFilter<T> filter) {
		super(serialNumber, it, filter);
		this.serialNumber = serialNumber;
		this.filter = filter;
	}

	@Override
	public boolean hasNext() {
		while (next == null && super.hasNext()) {
			if (!filter.emit(serialNumber, next = super.next())) {
				next = null;
			}
		}
		return next != null;
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		T next = this.next;
		this.next = null;
		return next;
	}

}