package org.utilities.symbolicmath.timeseries.store.cache;

public class SimpleCache implements Cache {

	private long pointer;
	private Double cache;

	@Override
	public void put(long i, Double value) {
		pointer = i;
		cache = value;
	}

	private void validate(long i) {
		if (i != pointer) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public Double get(long i) {
		validate(i);
		return cache;
	}

	@Override
	public Double[] get(long from, long to) {
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
