package org.utilities.symbolicmath.store;

import java.time.Instant;

import org.utilities.core.util.function.ArrayFactory;

//TODO move to timeseries
public class SSLocalTimeSeries<T> implements SymbolStoreTimeSeries<T> {

	private int step;
	private SSLocal<T> memories;

	public SSLocalTimeSeries(int step, ArrayFactory<T> factory) {
		this.step = step;
		this.memories = new SSLocal<>(factory);
	}

	private int parseIndex(long time) {
		return (int) (time / step);
	}

	@Override
	public Instant time() {
		return Instant.ofEpochMilli(step * memories.getIndex());
	}

	@Override
	public void put(String label, T value) {
		memories.put(label, value);
	}

	@Override
	public T get(String label) {
		return memories.get(label);
	}

	@Override
	public T lag(String label, int lag) {
		return memories.lag(label, parseIndex(lag));
	}

	@Override
	public T[] range(String label, int window) {
		return memories.range(label, parseIndex(window));
	}

	public void put(long unixtime, String label, T value) {
		memories.put(parseIndex(unixtime), label, value);
	}

	public void clear() {
		memories.clear();
	}

	@Override
	public void subscribe(SymbolStoreConsumer subscriber) {
		memories.subscribe(subscriber.getLabel(), subscriber.getWindow() / step);
	}
}
