package org.utilities.symbolicmath.algebra;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Power<S> extends UnaryOperator<S, Double, Double> {

	public Power(Value<S, Double> a, double exponent) {
		super(x -> Math.pow(x, exponent), a);
	}

}
