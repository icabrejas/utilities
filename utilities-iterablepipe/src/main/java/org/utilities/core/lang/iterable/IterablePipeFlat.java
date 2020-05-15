package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IterablePipeFlat<T> implements IterablePipe<T> {

	private Iterable<? extends Iterable<T>> it;

	public IterablePipeFlat(Iterable<? extends Iterable<T>> it) {
		this.it = it;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(it.iterator());
	}

	private static class It<T> implements Iterator<T> {

		private Iterator<? extends Iterable<T>> it;
		private Iterator<T> current;

		public It(Iterator<? extends Iterable<T>> it) {
			this.it = it;
			while (current == null && it.hasNext()) {
				current = it.next()
						.iterator();
			}
		}

		@Override
		public boolean hasNext() {
			while ((current == null || !current.hasNext()) && it.hasNext()) {
				current = it.next()
						.iterator();
			}
			return current != null && current.hasNext();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return current.next();
		}

	}

}
