
package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IterablePipeRepetition<T> implements IterablePipe<T> {

	private Iterable<T> it;
	private int times;

	public IterablePipeRepetition(Iterable<T> it, int times) {
		this.it = it;
		this.times = times;
	}

	public static <T> IterablePipeRepetition<T> newInstance(Iterable<T> it, int times) {
		return new IterablePipeRepetition<>(it, times);
	}
	
	@Override
	public Iterator<T> iterator() {
		return new It(it, times);
	}

	private class It implements Iterator<T> {

		private Iterable<T> iterable;
		private int times;
		private Iterator<T> it;

		public It(Iterable<T> iterable, int times) {
			this.iterable = iterable;
			this.times = times - 1;
			this.it = iterable.iterator();
		}

		@Override
		public boolean hasNext() {
			while (!it.hasNext() && 0 < times--) {
				it = iterable.iterator();
			}
			return it.hasNext();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return it.next();
		}

	}
}
