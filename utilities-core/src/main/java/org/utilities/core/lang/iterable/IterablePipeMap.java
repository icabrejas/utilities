package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.function.Function;

public class IterablePipeMap<T, R> implements IterablePipe<R> {

	private Iterable<T> it;
	private Function<T, R> mapper;

	public IterablePipeMap(Iterable<T> it, Function<T, R> mapper) {
		this.it = it;
		this.mapper = mapper;
	}

	@Override
	public Iterator<R> iterator() {
		return new It<>(it.iterator(), mapper);
	}

	private static class It<T, R> implements Iterator<R> {

		private Iterator<T> it;
		private Function<T, R> mapper;

		public It(Iterator<T> it, Function<T, R> mapper) {
			this.it = it;
			this.mapper = mapper;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public R next() {
			return mapper.apply(it.next());
		}

	}

}
