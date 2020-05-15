package org.utilities.core;

import java.util.Map;
import java.util.function.Supplier;

public class UtilitiesMap {

	public static <K, V> V getOrFill(Map<K, V> map, K key, Supplier<V> factory) {
		V value = map.get(key);
		if (value == null) {
			value = factory.get();
			map.put(key, value);
		}
		return value;
	}

}
