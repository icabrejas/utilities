package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Xor<S> extends BinaryOperator<S, Boolean, Boolean, Boolean> {

	public Xor(Value<S, Boolean> a, Value<S, Boolean> b) {
		super(Boolean::logicalXor, a, b);
	}

}
