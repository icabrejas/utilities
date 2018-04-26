package org.utilities.symbolicmath.algebra;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Exponential<S> extends UnaryOperator<S, Double, Double> {

	public Exponential(Value<S, Double> a) {
		super(Math::exp, a);
	}

}
