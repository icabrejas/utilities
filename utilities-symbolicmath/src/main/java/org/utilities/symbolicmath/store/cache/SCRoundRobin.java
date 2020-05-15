package org.utilities.symbolicmath.store.cache;

import java.util.Arrays;

import org.utilities.core.util.function.ArrayFactory;

public class SCRoundRobin<T> implements SymbolCache<T> {

	private long pointer;
	private ArrayFactory<T> factory;
	private T[] cache;

	public SCRoundRobin(int size, ArrayFactory<T> factory) {
		this.factory = factory;
		this.cache = this.factory.create(size);
	}

	private int mod(long i) throws IndexOutOfBoundsException {
		return (int) (i % cache.length);
	}

	private void checkLeftIndex(long i) {
		if (i <= pointer - cache.length | pointer < i) {
			throw new IndexOutOfBoundsException();
		}
	}

	private void checkLeftIndex(long from, long to) {
		if (to <= from) {
			throw new IndexOutOfBoundsException();
		}
		checkLeftIndex(from);
		checkLeftIndex(to);
	}

	private void checkRightIndex(long i) {
		if (i < pointer) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void put(long i, T value) throws IndexOutOfBoundsException {
		checkRightIndex(i);
		if (cache.length <= i - pointer) {
			clear();
		} else if (pointer < i) {
			clear(mod(pointer + 1), mod(i));
		}
		pointer = i;
		cache[mod(pointer)] = value;
	}

	private void clear(int from, int to) {
		if (from < to) {
			Arrays.fill(cache, from, to, null);
		} else if (to < from) {
			Arrays.fill(cache, 0, to, null);
			Arrays.fill(cache, from, cache.length, null);
		}
	}

	public int lenght() {
		return cache.length;
	}

	@Override
	public T get(long i) {
		checkLeftIndex(i);
		return cache[mod(i)];
	}

	@Override
	public T[] range(long from, long to) {
		checkLeftIndex(from + 1, to);
		return get(mod(from + 1), mod(to + 1));
	}

	private T[] get(int from, int to) {
		T[] range;
		if (from < to) {
			range = this.factory.create(to - from);
			if (from < 0) {
				System.arraycopy(cache, 0, range, -from, range.length + from);
			} else {
				System.arraycopy(cache, from, range, 0, range.length);
			}
		} else {
			range = this.factory.create(to + cache.length - from);
			System.arraycopy(cache, from, range, 0, cache.length - from);
			System.arraycopy(cache, 0, range, range.length - to, to);
		}
		return range;
	}

	@Override
	public void clear() {
		this.cache = this.factory.create(cache.length);
	}

	@Override
	public String toString() {
		return "RoundRobinCache [pointer=" + pointer + ", cache=" + cache.length + "]";
	}

}
