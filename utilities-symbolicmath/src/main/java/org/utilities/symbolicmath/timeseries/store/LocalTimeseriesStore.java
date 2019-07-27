package org.utilities.symbolicmath.timeseries.store;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.utilities.symbolicmath.timeseries.store.cache.Cache;
import org.utilities.symbolicmath.timeseries.store.cache.KeyValueCache;
import org.utilities.symbolicmath.timeseries.store.cache.RoundRobinCache;
import org.utilities.symbolicmath.timeseries.store.cache.SimpleCache;

public class LocalTimeseriesStore implements TimeSeriesStore {

	// TODO change by Instant
	private Long currentTime, step;
	private Map<String, Cache> memories = new HashMap<>();

	public LocalTimeseriesStore(Map<String, Long> windows, long step) {
		this.step = step;
		for (Map.Entry<String, Long> window : windows.entrySet()) {
			int size = 1 + (int) (window.getValue() / step);
			if (size == 1) {
				memories.put(window.getKey(), new SimpleCache());
			} else {
				memories.put(window.getKey(), new RoundRobinCache(size));
			}
		}
	}

	public LocalTimeseriesStore(Map<String, Long> windows) {
		this.step = 1L;
		for (Map.Entry<String, Long> window : windows.entrySet()) {
			long size = window.getValue();
			if (size == 0) {
				memories.put(window.getKey(), new SimpleCache());
			} else {
				memories.put(window.getKey(), new KeyValueCache(window.getValue()));
			}
		}
	}

	private int parseIndex(long time) {
		return (int) (time / step);
	}

	@Override
	public Instant time() {
		return Instant.ofEpochMilli(currentTime);
	}

	@Override
	public Double get(String label) {
		Cache memory = memories.get(label);
		if (memory != null) {
			return memory.get(parseIndex(currentTime));
		} else {
			return null;
		}
	}

	@Override
	public Double lag(String label, long lag) {
		Cache memory = memories.get(label);
		if (memory != null) {
			return memory.get(parseIndex(currentTime - lag));
		} else {
			return null;
		}
	}

	@Override
	public Double[] range(String label, long window) {
		Cache memory = memories.get(label);
		if (memory != null) {
			return memory.get(parseIndex(currentTime - window), parseIndex(currentTime));
		} else {
			return null;
		}
	}

	public void put(long unixtime, String label, Double value) {
		if (currentTime != null && unixtime < currentTime) {
			throw new IndexOutOfBoundsException();
		}
		if (currentTime == null || currentTime != unixtime) {
			currentTime = unixtime;
			for (Cache cache : memories.values()) {
				cache.put(parseIndex(currentTime), null);
			}
		}
		Cache memory = memories.get(label);
		if (memory != null) {
			memory.put(parseIndex(currentTime), value);
		}
	}

	public void clear() {
		for (Cache memory : memories.values()) {
			memory.clear();
		}
	}

}
