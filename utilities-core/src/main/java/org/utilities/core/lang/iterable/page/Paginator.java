package org.utilities.core.lang.iterable.page;

import java.util.List;

public interface Paginator<T> {

	List<T> page(int skip, int limit);

}
