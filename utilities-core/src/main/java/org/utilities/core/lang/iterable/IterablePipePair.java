package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.utilities.core.util.pair.Pair;
import org.utilities.core.util.pair.PairImpl;

public class IterablePipePair<X, Y> implements IterablePipe<Pair<X, Y>> {

	private Iterable<X> x;
	private Iterable<Y> y;

	public IterablePipePair(Iterable<X> x, Iterable<Y> y) {
		this.x = x;
		this.y = y;
	}

	public static <X, Y> IterablePipePair<X, Y> from(Iterable<X> x, Iterable<Y> y) {
		return new IterablePipePair<>(x, y);
	}

	@Override
	public Iterator<Pair<X, Y>> iterator() {
		return new It<>(x.iterator(), y.iterator());
	}

	private static class It<X, Y> implements Iterator<Pair<X, Y>> {

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
		public Pair<X, Y> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			X x = this.x.hasNext() ? this.x.next() : null;
			Y y = this.y.hasNext() ? this.y.next() : null;
			return new PairImpl<>(x, y);
		}

	}

}
