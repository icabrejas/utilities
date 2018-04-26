package org.utilities.symbolicmath.operator;

import java.util.function.Function;

import org.utilities.symbolicmath.value.Value;

public class UnaryOperator<S, A, V> implements Value<S, V> {

	private Function<A, V> func;
	private Value<S, A> a;

	public UnaryOperator(Function<A, V> func, Value<S, A> a) {
		this.func = func;
		this.a = a;
	}

	@Override
	public V apply(S store) {
		A a = this.a.apply(store);
		return a != null ? func.apply(a) : null;
	}

}
