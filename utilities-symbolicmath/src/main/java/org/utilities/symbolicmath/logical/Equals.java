package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Equals<S> extends BinaryOperator<S, Double, Double, Boolean> {

	public Equals(Value<S, Double> a, Value<S, Double> b) {
		super((x, y) -> x == y, a, b);
	}

}
