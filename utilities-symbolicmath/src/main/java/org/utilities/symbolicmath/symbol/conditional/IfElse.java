package org.utilities.symbolicmath.symbol.conditional;

import org.utilities.symbolicmath.symbol.Symbol;

public class IfElse<S, V> implements Symbol<S, V> {

	private Symbol<S, Boolean> condition;
	private Symbol<S, V> ifThen;
	private Symbol<S, V> elseThen;

	public IfElse(Symbol<S, Boolean> condition, Symbol<S, V> ifThen) {
		this(condition, ifThen, Symbol.empty());
	}

	public IfElse(Symbol<S, Boolean> condition, Symbol<S, V> ifThen, Symbol<S, V> elseThen) {
		this.condition = condition;
		this.ifThen = ifThen;
		this.elseThen = elseThen;
	}

	@Override
	public V apply(S store) {
		Boolean condition = this.condition.apply(store);
		if (condition == null) {
			return null;
		} else if (condition) {
			return ifThen.apply(store);
		} else {
			return elseThen.apply(store);
		}
	}

}
