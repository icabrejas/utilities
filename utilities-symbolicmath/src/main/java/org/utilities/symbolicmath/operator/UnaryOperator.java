package org.utilities.symbolicmath.operator;

import java.util.function.Function;

import org.utilities.symbolicmath.value.Value;

public class UnaryOperator<S, X, V> implements Value<S, V> {

	private Function<X, V> func;
	private Value<S, X> x;

	public UnaryOperator(Function<X, V> func, Value<S, X> x) {
		this.func = func;
		this.x = x;
	}

	@Override
	public V apply(S store) {
		X x = this.x.apply(store);
		if (x != null) {
			return func.apply(x);
		}
		return null;
	}

}
