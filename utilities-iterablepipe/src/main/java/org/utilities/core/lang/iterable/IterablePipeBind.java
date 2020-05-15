package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IterablePipeBind<T> implements IterablePipe<T> {

	private Iterable<T> first;
	private Iterable<T> second;

	public IterablePipeBind(Iterable<T> first, Iterable<T> second) {
		this.first = first;
		this.second = second;
	}

	public static <T> IterablePipe<T> create(Iterable<? extends Iterable<T>> iterables) {
		IterablePipe<T> bind = null;
		for (Iterable<T> iterable : iterables) {
			if (bind == null) {
				bind = IterablePipe.create(iterable);
			} else {
				bind = new IterablePipeBind<>(bind, iterable);
			}
		}
		return bind;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(first.iterator(), second.iterator());
	}

	private static class It<T> implements Iterator<T> {

		private Iterator<T> first;
		private Iterator<T> second;

		public It(Iterator<T> first, Iterator<T> second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public boolean hasNext() {
			return first.hasNext() || second.hasNext();
		}

		@Override
		public T next() {
			if (first.hasNext()) {
				return first.next();
			} else if (second.hasNext()) {
				return second.next();
			} else {
				throw new NoSuchElementException();
			}
		}

	}

}
