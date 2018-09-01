package org.utilities.symbolicmath.algebra;

import org.utilities.symbolicmath.Algebra;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Addition<S> extends BinaryOperator<S, Double, Double, Double> {

	public Addition(Value<S, Double> a, Value<S, Double> b) {
		super(Algebra::add, a, b);
	}

}
