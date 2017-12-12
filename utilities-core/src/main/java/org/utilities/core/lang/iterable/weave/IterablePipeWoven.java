package org.utilities.core.lang.iterable.weave;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.IterablePipe;

public class IterablePipeWoven<T> implements IterablePipe<T> {

	private Comparator<Map.Entry<Integer, T>> selector;
	private Iterable<? extends Iterable<T>> sources;

	public IterablePipeWoven(Comparator<Map.Entry<Integer, T>> selector, Iterable<? extends Iterable<T>> sources) {
		this.selector = selector;
		this.sources = sources;
	}

	public static <T> IterablePipeWoven<T> newInstance(Comparator<T> comparator,
			Iterable<? extends Iterable<T>> sources) {
		Comparator<Map.Entry<Integer, T>> selector = Comparator.comparing(Map.Entry::getValue, comparator);
		selector = selector.thenComparing(Map.Entry::getKey);
		return new IterablePipeWoven<>(selector, sources);
	}

	@Override
	public Iterator<T> iterator() {
		return It.newInstace(sources, selector);
	}

	public static class It<T> implements Iterator<T> {

		private Iterable<? extends Iterator<T>> sources;
		private Comparator<Map.Entry<Integer, T>> selector;
		private Map<Integer, T> cache;

		public It(Iterable<? extends Iterator<T>> sources, Comparator<Map.Entry<Integer, T>> selector) {
			this.sources = sources;
			this.selector = selector;
			this.cache = new HashMap<>();
		}

		public static <T> It<T> newInstace(Iterable<? extends Iterable<T>> sources,
				Comparator<Map.Entry<Integer, T>> selector) {
			List<Iterator<T>> iterators = IterablePipe.newInstance(sources)
					.map(Iterable::iterator)
					.toList();
			return new It<>(iterators, selector);
		}

		@Override
		public boolean hasNext() {
			populateCurrent();
			return !cache.isEmpty();
		}

		private void populateCurrent() {
			int i = 0;
			for (Iterator<T> it : sources) {
				if (!cache.containsKey(i) && it.hasNext()) {
					cache.put(i, it.next());
				}
				i++;
			}
		}

		@Override
		public T next() {
			if (hasNext()) {
				Map.Entry<Integer, T> next = lowestIndex();
				cache.remove(next.getKey());
				return next.getValue();
			} else {
				throw new NoSuchElementException();
			}
		}

		public Map.Entry<Integer, T> lowestIndex() {
			return Collections.min(cache.entrySet(), selector);
		}

	}

}
