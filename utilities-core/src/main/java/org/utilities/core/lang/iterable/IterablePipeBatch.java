package org.utilities.core.lang.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.utilities.core.util.function.BiPredicatePlus;

public class IterablePipeBatch<T> implements IterablePipe<List<T>> {

	private Iterable<T> it;
	private Semaphore<T> semaphore;

	public IterablePipeBatch(Iterable<T> it, Semaphore<T> semaphore) {
		this.it = it;
		this.semaphore = semaphore;
	}

	@Override
	public Iterator<List<T>> iterator() {
		return new It<>(it.iterator(), semaphore);
	}

	public static interface Semaphore<T> {

		boolean store(T prev, T current);

		public static <T> Semaphore<T> newInstance(Predicate<T> predicate) {
			return (prev, current) -> predicate.test(current);
		}

		public static <T> Semaphore<T> newInstance(BiPredicate<T, T> biPredicate) {
			return biPredicate::test;
		}

		public static <T, U> Semaphore<T> newInstance(BiPredicate<T, U> biPredicate, U u) {
			return newInstance(BiPredicatePlus.parsePredicate(biPredicate, u));
		}

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
			return !batch.isEmpty();
		}

		@Override
		public List<T> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			List<T> next = new ArrayList<>(batch);
			fill();
			return next;
		}

		private void fill() {
			batch.clear();
			T prev = null;
			if (current != null) {
				batch.add(prev = current);
				current = null;
			}
			while (it.hasNext() && semaphore.store(prev, current = it.next())) {
				batch.add(prev = current);
				current = null;
			}
		}

	}
}
