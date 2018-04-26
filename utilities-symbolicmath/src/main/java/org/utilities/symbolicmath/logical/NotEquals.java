package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class NotEquals<S> extends BinaryOperator<S, Double, Double, Boolean> {

	public NotEquals(Value<S, Double> a, Value<S, Double> b) {
		super((x, y) -> x != y, a, b);
	}

}
