package org.utilities.core.lang.iterable.batch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.tracker.IterablePipeTracked;

public class IterablePipeBatch<T> implements IterablePipe<List<T>> {

	private IterablePipeTracked<T> it;
	private Semaphore<T> semaphore;

	public IterablePipeBatch(Iterable<T> it, Semaphore<T> semaphore) {
		this.it = new IterablePipeTracked<>(it, semaphore);
		this.semaphore = semaphore;
	}

	@Override
	public Iterator<List<T>> iterator() {
		return new It<>(it.iterator(), semaphore);
	}

	private static class It<T> implements Iterator<List<T>> {

		private Iterator<T> it;
		private Semaphore<T> semaphore;
		private List<T> batch = new ArrayList<>();
		private T current;

		public It(Iterator<T> it, Semaphore<T> semaphore) {
			this.it = it;
			this.semaphore = semaphore;
			fill();
		}

		@Override
		public boolean hasNext() {
			if (batch.isEmpty()) {
				fill();
			}
			return !batch.isEmpty();
		}

		private void fill() {
			T prev = previous();
			while (it.hasNext() && semaphore.store(prev, current = it.next())) {
				batch.add(prev = current);
				current = null;
			}
		}

		private T previous() {
			T prev = null;
			if (current != null) {
				batch.add(prev = current);
				current = null;
			} else if (it.hasNext()) {
				batch.add(prev = it.next());
			}
			return prev;
		}

		@Override
		public List<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			List<T> next = new ArrayList<>(batch);
			batch.clear();
			return next;
		}

	}
	
}
