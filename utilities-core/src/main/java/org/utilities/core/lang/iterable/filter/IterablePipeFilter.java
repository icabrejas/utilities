package org.utilities.core.lang.iterable.filter;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.tracker.IterablePipeTracked;

public class IterablePipeFilter<T> implements IterablePipe<T> {

	private IterablePipeTracked<T> it;
	private Filter<T> filter;

	public IterablePipeFilter(Iterable<T> it, Filter<T> filter) {
		this.it = new IterablePipeTracked<>(it, filter);
		this.filter = filter;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(it.iterator(), filter);
	}

	private static class It<T> implements Iterator<T> {

		private Iterator<T> it;
		private Filter<T> filter;
		private T next;

		public It(Iterator<T> it, Filter<T> filter) {
			this.it = it;
			this.filter = filter;
		}

		@Override
		public boolean hasNext() {
			while (next == null && it.hasNext()) {
				if (!filter.test(next = it.next())) {
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

}
