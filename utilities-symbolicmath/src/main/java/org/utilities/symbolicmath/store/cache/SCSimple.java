package org.utilities.symbolicmath.store.cache;

public class SCSimple<T> implements SymbolCache<T> {

	private long pointer;
	private T cache;

	@Override
	public void put(long i, T value) {
		pointer = i;
		cache = value;
	}

	private void validate(long i) {
		if (i != pointer) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public T get(long i) {
		validate(i);
		return cache;
	}

	@Override
	public T[] range(long from, long to) {
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void clear() {
		cache = null;
	}

	@Override
	public String toString() {
		return "SimpleCache [pointer=" + pointer + ", cache=" + cache + "]";
	}

}
