package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.utilities.core.util.lambda.LambdaValue;
import org.utilities.core.util.pair.Pair;
import org.utilities.core.util.pair.PairImpl;

public class IterablePipeCartesian<X, Y> implements IterablePipe<Pair<X, Y>> {

	private Iterable<X> x;
	private Iterable<Y> y;

	public IterablePipeCartesian(Iterable<X> x, Iterable<Y> y) {
		this.x = x;
		this.y = y;
	}

	public static <X, Y> IterablePipeCartesian<X, Y> from(Iterable<X> x, Iterable<Y> y) {
		return new IterablePipeCartesian<>(x, y);
	}

	@Override
	public Iterator<Pair<X, Y>> iterator() {
		return new It<>(x, y);
	}

	private static class It<X, Y> implements Iterator<Pair<X, Y>> {

		private Iterable<Y> y;
		private X x;
		private Iterator<X> itX;
		private Iterator<Y> itY;
		private LambdaValue<Pair<X, Y>> next = new LambdaValue<>();

		public It(Iterable<X> x, Iterable<Y> y) {
			this.y = y;
			this.itX = x.iterator();
			this.itY = y.iterator();
			if (itX.hasNext()) {
				this.x = itX.next();
			}
		}

		@Override
		public boolean hasNext() {
			if (!next.isPresent()) {
				fillNext();
			}
			return next.isPresent();
		}

		private void fillNext() {
			if (!itY.hasNext() && itX.hasNext()) {
				x = itX.next();
				itY = y.iterator();
			}
			if (itY.hasNext()) {
				next.set(new PairImpl<>(x, itY.next()));
			}
		}

		@Override
		public Pair<X, Y> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return next.remove();
		}

	}

}
