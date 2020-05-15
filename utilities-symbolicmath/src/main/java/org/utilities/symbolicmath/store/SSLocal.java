package org.utilities.symbolicmath.store;

import java.util.HashMap;
import java.util.Map;

import org.utilities.core.util.function.ArrayFactory;
import org.utilities.symbolicmath.store.cache.SCRoundRobin;
import org.utilities.symbolicmath.store.cache.SCSimple;
import org.utilities.symbolicmath.store.cache.SymbolCache;

public class SSLocal<T> implements SymbolStore<T> {

	private ArrayFactory<T> factory;
	private Long index;
	private Map<String, SymbolCache<T>> memories = new HashMap<>();

	public SSLocal(ArrayFactory<T> factory) {
		this.factory = factory;
	}

	public void moveTo(long index) {
		if (this.index != null && index < this.index) {
			throw new IndexOutOfBoundsException();
		}
		if (this.index == null) {
			this.index = index - 1;
		}
		if (this.index != index) {
			for (SymbolCache<T> cache : memories.values()) {
				if (cache instanceof SCRoundRobin) {
					int lenght = ((SCRoundRobin<T>) cache).lenght();
					for (long i = Math.max(this.index, index - lenght) + 1; i < index + 1; i++) {
						cache.put(i, null);
					}
				} else {
					cache.put(this.index, null);
				}
			}
			this.index = index;
		}
	}

	@Override
	public void put(String label, T value) {
		SymbolCache<T> memory = memories.get(label);
		if (memory == null) {
			memory = new SCSimple<>();
			memories.put(label, memory);
		}
		memory.put(this.index, value);
	}

	@Override
	public T get(String label) {
		SymbolCache<T> memory = memories.get(label);
		if (memory != null) {
			return memory.get(index);
		} else {
			return null;
		}
	}

	@Override
	public T lag(String label, int lag) {
		SymbolCache<T> memory = memories.get(label);
		if (memory != null) {
			return memory.get(index - lag);
		} else {
			return null;
		}
	}

	@Override
	public T[] range(String label, int window) {
		SymbolCache<T> memory = memories.get(label);
		if (memory != null) {
			return memory.range(index - window, index);
		} else {
			return null;
		}
	}

	public void put(long index, String label, T value) {
		moveTo(index);
		put(label, value);
	}

	public void clear() {
		for (SymbolCache<T> memory : memories.values()) {
			memory.clear();
		}
	}

	public Long getIndex() {
		return index;
	}

	@Override
	public void subscribe(SymbolStoreConsumer subscriber) {
		subscribe(subscriber.getLabel(), subscriber.getWindow());
	}

	public void subscribe(String label, int window) {
		SymbolCache<T> cache = memories.get(label);
		if (window == 1) {
			if (cache == null) {
				memories.put(label, new SCSimple<>());
			}
		} else {
			if (cache == null || length(cache) < window) {
				memories.put(label, new SCRoundRobin<>(window, factory));
			}
		}
	}

	private int length(SymbolCache<T> cache) {
		return cache instanceof SCSimple ? 1 : ((SCRoundRobin<T>) cache).lenght();
	}

}
