package org.utilities.core.lang.iterable;

import java.util.Iterator;

import org.utilities.core.util.concurrent.UtilitiesThread;

public class IterablePipeCache<T> implements IterablePipe<T> {

	private Iterable<T> it;

	public IterablePipeCache(Iterable<T> it) {
		this.it = it;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(it.iterator());
	}

	private static class It<T> implements Iterator<T> {

		private Iterator<T> it;
		private SingleIterator<T> next;

		public It(Iterator<T> it) {
			this.it = it;
			fillNext();
		}

		@Override
		public boolean hasNext() {
			waitForNext();
			return this.next.hasNext();
		}

		@Override
		public T next() {
			waitForNext();
			T next = this.next.next();
			fillNext();
			return next;
		}

		private void fillNext() {
			this.next = null;
			UtilitiesThread.run(() -> this.next = new SingleIterator<>(it));
		}

		private void waitForNext() {
			UtilitiesThread.wait(() -> next != null);
		}

	}

}
