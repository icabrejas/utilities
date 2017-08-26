package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingleIterator<T> implements Iterator<T> {

	private boolean hasNext;
	private T next;

	public SingleIterator(Iterator<T> it) {
		if (this.hasNext = it.hasNext()) {
			this.next = it.next();
		}
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		T next = this.next;
		hasNext = false;
		return next;
	}

}