package org.utilities.core.lang.iterable.limit;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.tracker.IterablePipeTracked;
import org.utilities.core.util.lambda.LambdaValue;

public class IterablePipeLimit<T> implements IterablePipe<T> {

	private IterablePipeTracked<T> it;
	private StopCriteria<T> stop;

	public IterablePipeLimit(Iterable<T> it, StopCriteria<T> stop) {
		this.it = new IterablePipeTracked<>(it, stop);
		this.stop = stop;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(it.iterator(), stop);
	}

	private static class It<T> implements Iterator<T> {

		private Iterator<T> it;
		private StopCriteria<T> stopCriteria;
		private LambdaValue<T> next = new LambdaValue<>();
		private boolean stop;

		public It(Iterator<T> it, StopCriteria<T> stopCriteria) {
			this.it = it;
			this.stopCriteria = stopCriteria;
		}

		@Override
		public boolean hasNext() {
			while (!next.isPresent() && it.hasNext()) {
				next.set(it.next());
				stop = stopCriteria.stop(next.get());
			}
			return next.isPresent() && !stop;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return next.remove();
		}

	}
}
