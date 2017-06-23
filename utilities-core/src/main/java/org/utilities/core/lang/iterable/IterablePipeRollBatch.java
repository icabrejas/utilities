package org.utilities.core.lang.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

// TODO 
public class IterablePipeRollBatch<T> implements IterablePipe<List<T>> {

	private Iterable<T> it;
	private Predicate<T> semaphore;

	public IterablePipeRollBatch(Iterable<T> it, Predicate<T> semaphore) {
		this.it = it;
		this.semaphore = semaphore;
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public Iterator<List<T>> iterator() {
		return new It<>(it.iterator(), semaphore);
	}

	private static class It<T> implements Iterator<List<T>> {

		private Iterator<T> it;
		private Predicate<T> semaphore;
		private List<T> batch = new ArrayList<>();
		private T next;

		public It(Iterator<T> it, Predicate<T> semaphore) {
			this.it = it;
			this.semaphore = semaphore;
		}

		@Override
		public boolean hasNext() {
			init();
			fill();
			return !batch.isEmpty();
		}

		private void init() {
			if (next == null && it.hasNext()) {
				next = it.next();
			}
		}

		private void fill() {
			if (batch.isEmpty() && next != null) {
				batch.add(next);
				next = it.hasNext() ? it.next() : null;
			}
			while (next != null && semaphore.test(next)) {
				batch.add(next);
				next = it.hasNext() ? it.next() : null;
			}
		}

		@Override
		public List<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return empty(batch);
		}

		private List<T> empty(List<T> list) {
			List<T> next = new ArrayList<>(list);
			list.clear();
			return next;
		}

	}
}
