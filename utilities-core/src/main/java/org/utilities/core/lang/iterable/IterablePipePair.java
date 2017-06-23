package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IterablePipePair<X, Y> implements IterablePipe<Entry<X, Y>> {

	private Iterable<X> x;
	private Iterable<Y> y;

	public IterablePipePair(Iterable<X> x, Iterable<Y> y) {
		this.x = x;
		this.y = y;
	}

	public static <X, Y> IterablePipePair<X, Y> newInstance(Iterable<X> x, Iterable<Y> y) {
		return new IterablePipePair<>(x, y);
	}

	@Override
	public Iterator<Entry<X, Y>> iterator() {
		return new It<>(x.iterator(), y.iterator());
	}

	private static class It<X, Y> implements Iterator<Entry<X, Y>> {

		private Iterator<X> x;
		private Iterator<Y> y;

		public It(Iterator<X> x, Iterator<Y> y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean hasNext() {
			return x.hasNext() || y.hasNext();
		}

		@Override
		public Entry<X, Y> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			X x = this.x.hasNext() ? this.x.next() : null;
			Y y = this.y.hasNext() ? this.y.next() : null;
			return Entry.newInstance(x, y);
		}

	}

}
