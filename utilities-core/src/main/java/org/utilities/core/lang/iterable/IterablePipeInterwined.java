package org.utilities.core.lang.iterable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.utilities.core.util.lambda.LambdaInt;

public class IterablePipeInterwined<T> implements IterablePipe<T> {

	private Comparator<It.Entry<T>> selector;
	private Iterable<? extends Iterable<T>> sources;

	public IterablePipeInterwined(Comparator<It.Entry<T>> selector, Iterable<? extends Iterable<T>> sources) {
		this.selector = selector;
		this.sources = sources;
	}

	public static <T> IterablePipeInterwined<T> newInstance(Comparator<T> comparator,
			Iterable<? extends Iterable<T>> sources) {
		Comparator<It.Entry<T>> selector = Comparator.comparing(It.Entry::getElement, comparator);
		selector = selector.thenComparing(Comparator.comparing(It.Entry::getIndex, Integer::compare));
		return new IterablePipeInterwined<>(selector, sources);
	}

	@Override
	public Iterator<T> iterator() {
		return It.newInstace(sources, selector);
	}

	public static class It<T> implements Iterator<T> {

		private Iterable<? extends Iterator<T>> sources;
		private Comparator<Entry<T>> selector;
		private List<Entry<T>> cache = new ArrayList<>();

		public It(Iterable<? extends Iterator<T>> sources, Comparator<Entry<T>> selector) {
			this.sources = sources;
			this.selector = selector;
			this.cache = new ArrayList<>();
			sources.forEach(it -> this.cache.add(null));
		}

		public static <T> It<T> newInstace(Iterable<? extends Iterable<T>> sources, Comparator<Entry<T>> selector) {
			List<Iterator<T>> iterators = IterablePipe.newInstance(sources)
					.map(Iterable::iterator)
					.toList();
			return new It<>(iterators, selector);
		}

		private void populateCurrent() {
			int i = -1;
			for (Iterator<T> it : sources) {
				if (cache.get(++i) == null && it.hasNext()) {
					cache.set(i, new Entry<>(i, it.next()));
				}
			}
		}

		@Override
		public boolean hasNext() {
			populateCurrent();
			boolean hasNext = false;
			for (int i = 0; i < cache.size() && !hasNext; i++) {
				hasNext = cache.get(i) != null;
			}
			return hasNext;
		}

		@Override
		public T next() {
			if (hasNext()) {
				int lowestIndex = lowestIndex();
				Entry<T> next = cache.get(lowestIndex);
				cache.set(lowestIndex, null);
				return next.getElement();
			} else {
				throw new NoSuchElementException();
			}
		}

		public int lowestIndex() {
			int lowestIndex = -1;
			for (int i = 0; i < cache.size(); i++) {
				Entry<T> current = cache.get(i);
				if (current != null) {
					if (lowestIndex < 0) {
						lowestIndex = i;
					} else {
						Entry<T> lowest = cache.get(lowestIndex);
						if (0 < selector.compare(lowest, current)) {
							lowestIndex = i;
						}
					}
				}
			}
			return lowestIndex;
		}

		public static <T> Comparator<Entry<T>> shuffle(List<Iterator<T>> sources) {
			// FIXME how use resettable??
			LambdaInt index = new LambdaInt(0);
			return (x, y) -> {
				if (index.add(1) < sources.size()) {
					return index.get();
				} else {
					return index.set(0);
				}
			};
		}

		public static class Entry<T> {

			private int index;
			private T element;

			public Entry(int index, T element) {
				this.index = index;
				this.element = element;
			}

			public int getIndex() {
				return index;
			}

			public T getElement() {
				return element;
			}

			@Override
			public String toString() {
				return "Entry [index=" + index + ", element=" + element + "]";
			}

		}

	}

}
