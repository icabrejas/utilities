package org.utilities.symbolicmath.algebra;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class SquareRoot<S> extends UnaryOperator<S, Double, Double> {

	public SquareRoot(Value<S, Double> a) {
		super(Math::sqrt, a);
	}

}
