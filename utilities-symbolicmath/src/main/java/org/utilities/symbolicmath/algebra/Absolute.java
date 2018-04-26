package org.utilities.symbolicmath.algebra;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Absolute<S> extends UnaryOperator<S, Double, Double> {

	public Absolute(Value<S, Double> a) {
		super(Math::abs, a);
	}

}
