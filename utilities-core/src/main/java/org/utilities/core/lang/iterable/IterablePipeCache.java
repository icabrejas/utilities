package org.utilities.core.lang.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.utilities.core.util.concurrent.UtilitiesThread;

public class IterablePipeCache<T> implements IterablePipe<T> {

	private Iterable<T> it;
	private int bufferSize;

	public IterablePipeCache(Iterable<T> it, int bufferSize) {
		this.it = it;
		this.bufferSize = bufferSize;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(it.iterator(), bufferSize);
	}

	public static class It<T> implements Iterator<T> {

		private static final ExecutorService POOL = UtilitiesThread.newThreadPool(10);

		private Iterator<T> it;
		private int bufferSize;
		private List<T> currentPage = new ArrayList<>();
		private List<T> nextPage = new ArrayList<>();
		private Future<?> filling;

		public It(Iterator<T> it, int bufferSize) {
			this.it = it;
			this.bufferSize = bufferSize;
			fillNext();
		}

		@Override
		public boolean hasNext() {
			if (currentPage.isEmpty()) {
				fillCurrent();
			}
			return !currentPage.isEmpty();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return currentPage.remove(0);
		}

		private void fillCurrent() {
			while (isNextPageFilling()) {
				UtilitiesThread.sleepQuietly(100);
			}
			currentPage = nextPage;
			fillNext();
		}

		private boolean isNextPageFilling() {
			return filling != null && !filling.isDone();
		}

		private void fillNext() {
			filling = POOL.submit(() -> {
				nextPage = new ArrayList<>();
				while (nextPage.size() < bufferSize && it.hasNext()) {
					nextPage.add(it.next());
				}
			});
		}

	}

}
