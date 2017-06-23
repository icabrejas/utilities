package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class IterablePipeLimit<T> implements IterablePipe<T> {

	private Iterable<T> it;
	private Predicate<T> stopCriteria;

	public IterablePipeLimit(Iterable<T> it, Predicate<T> stopCriteria) {
		this.it = it;
		this.stopCriteria = stopCriteria;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(it.iterator(), stopCriteria);
	}

	private static class It<T> implements Iterator<T> {

		private Iterator<T> it;
		private Predicate<T> stopCriteria;
		private T next;
		private boolean stop;

		public It(Iterator<T> it, Predicate<T> stopCriteria) {
			this.it = it;
			this.stopCriteria = stopCriteria;
		}

		@Override
		public boolean hasNext() {
			while (next == null && it.hasNext()) {
				next = it.next();
				stop = !stopCriteria.test(next);
			}
			return next != null && !stop;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return empty();
		}

		private T empty() {
			T next = this.next;
			this.next = null;
			return next;
		}

	}
}
