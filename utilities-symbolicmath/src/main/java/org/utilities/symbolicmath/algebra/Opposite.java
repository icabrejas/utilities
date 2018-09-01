package org.utilities.symbolicmath.algebra;

import org.utilities.symbolicmath.Algebra;
import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Opposite<S> extends UnaryOperator<S, Double, Double> {

	public Opposite(Value<S, Double> x) {
		super(Algebra::opposite, x);
	}

}
