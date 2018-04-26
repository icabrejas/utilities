package org.utilities.symbolicmath.operator;

import java.util.function.BiFunction;

import org.utilities.symbolicmath.value.Value;

public class BinaryOperator<S, A, B, V> implements Value<S, V> {

	private BiFunction<A, B, V> func;
	private Value<S, A> a;
	private Value<S, B> b;

	public BinaryOperator(BiFunction<A, B, V> func, Value<S, A> a, Value<S, B> b) {
		this.func = func;
		this.a = a;
		this.b = b;
	}

	@Override
	public V apply(S store) {
		A a = this.a.apply(store);
		if (a != null) {
			B b = this.b.apply(store);
			if (b != null) {
				return func.apply(a, b);
			}
		}
		return null;
	}

}
