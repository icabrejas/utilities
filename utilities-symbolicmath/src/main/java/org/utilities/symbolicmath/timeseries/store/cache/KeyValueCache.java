package org.utilities.symbolicmath.timeseries.store.cache;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class KeyValueCache implements Cache {

	private long maxWindow;
	private TreeMap<Long, Double> cache = new TreeMap<>();

	public KeyValueCache(long maxWindow) {
		this.maxWindow = maxWindow;
	}

	@Override
	public void put(long i, Double value) {
		cache = new TreeMap<>(cache.subMap(i - maxWindow, i));
		cache.put(i, value);
	}

	@Override
	public Double get(long i) {
		return cache.get(i);
	}

	@Override
	public Double[] get(long from, long to) {
		List<Double> values = cache.subMap(from, false, to, true)
				.values()
				.stream()
				.collect(Collectors.toList());
		return values.toArray(new Double[values.size()]);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public String toString() {
		return "KeyValueCache [maxWindow=" + maxWindow + ", cache=" + cache.size() + "]";
	}

}
