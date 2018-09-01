package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.Logical;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Lower<S> extends BinaryOperator<S, Double, Double, Boolean> {

	public Lower(Value<S, Double> x, Value<S, Double> y, boolean extrict) {
		super(extrict ? Logical::extrictLower : Logical::lower, x, y);
	}

}
