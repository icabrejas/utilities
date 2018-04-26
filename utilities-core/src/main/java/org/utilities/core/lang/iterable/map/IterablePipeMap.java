package org.utilities.core.lang.iterable.map;

import java.util.Iterator;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.tracker.IterablePipeTracked;

public class IterablePipeMap<T, R> implements IterablePipe<R> {

	private IterablePipeTracked<T> it;
	private Mapper<T, R> mapper;

	public IterablePipeMap(Iterable<T> it, Mapper<T, R> mapper) {
		this.it = new IterablePipeTracked<>(it, mapper);
		this.mapper = mapper;
	}

	public static <T, R> IterablePipeMap<T, R> from(Iterable<T> it, Mapper<T, R> mapper) {
		return new IterablePipeMap<>(it, mapper);
	}

	@Override
	public Iterator<R> iterator() {
		return new It<>(it.iterator(), mapper);
	}

	private static class It<T, R> implements Iterator<R> {

		private Iterator<T> it;
		private Mapper<T, R> mapper;

		public It(Iterator<T> it, Mapper<T, R> mapper) {
			this.it = it;
			this.mapper = mapper;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public R next() {
			return mapper.map(it.next());
		}

	}

}
