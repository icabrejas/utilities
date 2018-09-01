package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.Logical;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class NotEquals<S> extends BinaryOperator<S, Double, Double, Boolean> {

	public NotEquals(Value<S, Double> x, Value<S, Double> y) {
		super(Logical::distinct, x, y);
	}

}
