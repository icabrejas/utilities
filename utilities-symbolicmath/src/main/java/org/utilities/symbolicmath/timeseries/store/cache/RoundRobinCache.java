package org.utilities.symbolicmath.timeseries.store.cache;

public class RoundRobinCache implements Cache {

	private long pointer;
	private Double[] cache;

	public RoundRobinCache(int size) {
		this.cache = new Double[size];
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
		if (to < from) {
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
	public void put(long i, Double value) throws IndexOutOfBoundsException {
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
			for (int j = from; j < to; j++) {
				cache[j] = null;
			}
		} else if (to < from) {
			for (int j = from; j < cache.length; j++) {
				cache[j] = null;
			}
			for (int j = 0; j < to; j++) {
				cache[j] = null;
			}
		}
	}

	@Override
	public Double get(long i) {
		checkLeftIndex(i);
		return cache[mod(i)];
	}

	@Override
	public Double[] get(long from, long to) {
		checkLeftIndex(from + 1, to);
		return get(mod(from + 1), mod(to + 1));
	}

	private Double[] get(int from, int to) {
		Double[] range;
		if (from <= to) {
			range = new Double[to - from];
			System.arraycopy(cache, from, range, 0, to - from);
		} else {
			range = new Double[to + cache.length - from];
			System.arraycopy(cache, from, range, 0, cache.length - from);
			System.arraycopy(cache, 0, range, range.length - to, to);
		}
		return range;
	}

	@Override
	public void clear() {
		cache = new Double[cache.length];
	}

	@Override
	public String toString() {
		return "RoundRobinCache [pointer=" + pointer + ", cache=" + cache.length + "]";
	}

}
