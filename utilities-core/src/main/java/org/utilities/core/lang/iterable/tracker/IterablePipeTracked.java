package org.utilities.core.lang.iterable.tracker;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.IterablePipe;

public class IterablePipeTracked<T> implements IterablePipe<T> {

	private Iterable<T> it;
	private Tracker<T> tracker;

	public IterablePipeTracked(Iterable<T> it, Tracker<T> tracker) {
		this.it = it;
		this.tracker = tracker;
	}

	public static <T> IterablePipeTracked<T> newInstance(Iterable<T> it, Tracker<T> tracker) {
		return new IterablePipeTracked<>(it, tracker);
	}

	@Override
	public It<T> iterator() {
		return new It<>(it.iterator(), tracker);
	}

	public static class It<T> implements Iterator<T> {

		private Iterator<T> it;
		private Tracker<T> tracker;
		private boolean started = false;
		private boolean ended = false;

		public It(Iterator<T> it, Tracker<T> tracker) {
			this.it = it;
			this.tracker = tracker;
		}

		@Override
		public boolean hasNext() {
			if (!started) {
				notifyStart();
			}
			if (!ended && !it.hasNext()) {
				notifyEnd();
			}
			return !ended;
		}

		private void notifyStart() {
			tracker.onStart();
			started = true;
		}

		private void notifyEnd() {
			tracker.onEnd();
			ended = true;
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
