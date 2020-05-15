package org.utilities.core.lang.iterable.page;

import java.util.List;
import java.util.function.BiFunction;

public class PaginatorImpl<T> implements Paginator<T> {

	private BiFunction<Integer, Integer, List<T>> page;

	@Override
	public List<T> page(int skip, int limit) {
		return page.apply(skip, limit);
	}

	public PaginatorImpl<T> page(BiFunction<Integer, Integer, List<T>> page) {
		this.page = page;
		return this;
	}

}
