package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Or<S> extends BinaryOperator<S, Boolean, Boolean, Boolean> {

	public Or(Value<S, Boolean> a, Value<S, Boolean> b) {
		super(Boolean::logicalOr, a, b);
	}

}
