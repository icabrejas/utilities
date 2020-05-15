package org.utilities.symbolicmath.store.cache;

public interface SymbolCache<T> {

	void put(long index, T value);

	T get(long index);

	T[] range(long from, long to);

	void clear();

}
