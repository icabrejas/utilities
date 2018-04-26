package org.utilities.symbolicmath.value;

public class Constant<S, V> implements Value<S, V> {

	private V value;

	public Constant(V value) {
		this.value = value;
	}

	@Override
	public V apply(S store) {
		return value;
	}
	
}
