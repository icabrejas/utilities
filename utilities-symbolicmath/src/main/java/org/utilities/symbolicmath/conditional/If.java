package org.utilities.symbolicmath.conditional;

import org.utilities.symbolicmath.value.Value;

public class If<S, V> implements Value<S, V> {

	private Value<S, Boolean> condition;
	private Value<S, V> ifThen;

	public If(Value<S, Boolean> condition, Value<S, V> ifThen) {
		this.condition = condition;
		this.ifThen = ifThen;
	}

	@Override
	public V apply(S store) {
		Boolean condition = this.condition.apply(store);
		return (condition != null && condition) ? ifThen.apply(store) : null;
	}

}
