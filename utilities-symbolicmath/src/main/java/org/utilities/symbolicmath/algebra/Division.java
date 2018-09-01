package org.utilities.symbolicmath.algebra;

import org.utilities.symbolicmath.Algebra;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Division<S> extends BinaryOperator<S, Double, Double, Double> {

	public Division(Value<S, Double> a, Value<S, Double> b) {
		super(Algebra::divide, a, b);
	}

}
