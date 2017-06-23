package org.utilities.core.util.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class NotNullMap<K, V> {

	private Map<K, V> map;
	private Supplier<V> factory;

	public NotNullMap(Supplier<V> factory) {
		this.map = new HashMap<>();
		this.factory = factory;
	}

	public NotNullMap(Map<K, V> map, Supplier<V> factory) {
		this.map = map;
		this.factory = factory;
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public V get(K key) {
		return UtilitiesMap.getOrFill(map, key, factory);
	}

	public V put(K key, V value) {
		return map.put(key, value);
	}

	public void clear() {
		map.clear();
	}
	
}
