package org.utilities.core.lang.iterable.observer;

import java.util.Iterator;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.tracker.IterablePipeTracked;

public class IterablePipeObserved<T> implements IterablePipe<T> {

	private IterablePipeTracked<T> it;
	private Observer<T> observer;

	public IterablePipeObserved(Iterable<T> it, Observer<T> observer) {
		this.it = new IterablePipeTracked<>(it, observer);
		this.observer = observer;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(it.iterator(), observer);
	}

	private static class It<T> implements Iterator<T> {

		private Iterator<T> it;
		private Observer<T> observer;

		public It(Iterator<T> it, Observer<T> observer) {
			this.it = it;
			this.observer = observer;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public T next() {
			T next = it.next();
			observer.onNext(next);
			return next;
		}

	}

}
