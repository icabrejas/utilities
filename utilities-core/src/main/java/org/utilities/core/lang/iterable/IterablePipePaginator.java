package org.utilities.core.lang.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IterablePipePaginator<T> implements IterablePipe<T> {

	private final int limit;
	private Paginator<T> paginator;

	public IterablePipePaginator(int limit, Paginator<T> paginator) {
		this.limit = limit;
		this.paginator = paginator;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<T>(limit, paginator);
	}

	private static class It<T> implements Iterator<T> {

		private int skip, limit;
		private Paginator<T> paginator;
		private List<T> page = new ArrayList<>();

		public It(int limit, Paginator<T> paginator) {
			this.limit = limit;
			this.paginator = paginator;
		}

		@Override
		public boolean hasNext() {
			if (page.isEmpty() && skip % limit == 0) {
				fillNextPage();
			}
			return !page.isEmpty();
		}

		private void fillNextPage() {
			page = paginator.page(skip, limit);
			skip += page.size();
			if (page.size() < limit) {
				skip++;
			}
		}

		@Override
		public T next() {
			return page.remove(0);
		}

	}

	public static interface Paginator<T> {

		List<T> page(int skip, int limit);

	}

}
