package org.utilities.symbolicmath.conditional;

import org.utilities.symbolicmath.value.Value;

public class IfElse<S, V> implements Value<S, V> {

	private Value<S, Boolean> condition;
	private Value<S, V> ifThen;
	private Value<S, V> elseThen;

	public IfElse(Value<S, Boolean> condition, Value<S, V> ifThen, Value<S, V> elseThen) {
		this.condition = condition;
		this.ifThen = ifThen;
		this.elseThen = elseThen;
	}

	@Override
	public V apply(S store) {
		Boolean condition = this.condition.apply(store);
		if (condition != null) {
			return condition ? ifThen.apply(store) : elseThen.apply(store);
		} else {
			return null;
		}
	}

}
