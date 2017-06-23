package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class IterablePipeFilter<T> implements IterablePipe<T> {

	private Iterable<T> it;
	private Predicate<T> filter;

	public IterablePipeFilter(Iterable<T> it, Predicate<T> filter) {
		this.it = it;
		this.filter = filter;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(it.iterator(), filter);
	}

	private static class It<T> implements Iterator<T> {

		private Iterator<T> it;
		private Predicate<T> filter;
		private T next;

		public It(Iterator<T> it, Predicate<T> filter) {
			this.it = it;
			this.filter = filter;
		}

		@Override
		public boolean hasNext() {
			while (next == null && it.hasNext()) {
				next = it.next();
				if (!filter.test(next)) {
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
