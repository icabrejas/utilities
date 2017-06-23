package org.utilities.core.lang.iterable.cartesian;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.Entry;
import org.utilities.core.lang.iterable.IterablePipe;

// TODO
public class IterableCartesian<X, Y> implements IterablePipe<Entry<X, Y>> {

	private Iterable<X> x;
	private Iterable<Y> y;

	public IterableCartesian(Iterable<X> x, Iterable<Y> y) {
		this.x = x;
		this.y = y;
	}

	public static <X, Y> IterableCartesian<X, Y> newInstance(Iterable<X> x, Iterable<Y> y) {
		return new IterableCartesian<>(x, y);
	}

	@Override
	public Iterator<Entry<X, Y>> iterator() {
		return new It<>(x, y);
	}

	private static class It<X, Y> implements Iterator<Entry<X, Y>> {

		private Iterable<Y> ySrc;
		private Iterator<X> xIt;
		private Iterator<Y> yIt;
		private X xCurrent;

		public It(Iterable<X> xSrc, Iterable<Y> ySrc) {
			this.ySrc = ySrc;
			this.xIt = xSrc.iterator();
			this.yIt = ySrc.iterator();
			if (xIt.hasNext()) {
				xCurrent = xIt.next();
			}
		}

		@Override
		public boolean hasNext() {
			return xIt.hasNext() || yIt.hasNext();
		}

		@Override
		public Entry<X, Y> next() {
			if (!yIt.hasNext() && xIt.hasNext()) {
				xCurrent = xIt.next();
				yIt = ySrc.iterator();
			}
			if (yIt.hasNext()) {
				return Entry.newInstance(xCurrent, yIt.next());
			} else {
				throw new NoSuchElementException();
			}
		}

	}

}
