package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Greater<S> extends BinaryOperator<S, Double, Double, Boolean> {

	public Greater(Value<S, Double> a, Value<S, Double> b, boolean extrict) {
		super(extrict ? (x, y) -> x > y : (x, y) -> x >= y, a, b);
	}

}
