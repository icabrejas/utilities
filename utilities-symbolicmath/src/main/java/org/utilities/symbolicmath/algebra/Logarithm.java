package org.utilities.symbolicmath.algebra;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Logarithm<S> extends UnaryOperator<S, Double, Double> {

	public Logarithm(Value<S, Double> a) {
		super(Math::log, a);
	}

}
