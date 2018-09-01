package org.utilities.symbolicmath.operator;

import java.util.function.BiFunction;

import org.utilities.symbolicmath.value.Value;

public class BinaryOperator<S, X, Y, V> implements Value<S, V> {

	private BiFunction<X, Y, V> func;
	private Value<S, X> x;
	private Value<S, Y> y;

	public BinaryOperator(BiFunction<X, Y, V> func, Value<S, X> x, Value<S, Y> y) {
		this.func = func;
		this.x = x;
		this.y = y;
	}

	@Override
	public V apply(S store) {
		X x = this.x.apply(store);
		if (x != null) {
			Y y = this.y.apply(store);
			if (y != null) {
				return func.apply(x, y);
			}
		}
		return null;
	}

}
