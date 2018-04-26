package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class And<S> extends BinaryOperator<S, Boolean, Boolean, Boolean> {

	public And(Value<S, Boolean> a, Value<S, Boolean> b) {
		super(Boolean::logicalAnd, a, b);
	}

}
