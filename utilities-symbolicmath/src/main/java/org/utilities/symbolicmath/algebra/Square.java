package org.utilities.symbolicmath.algebra;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Square<S> extends UnaryOperator<S, Double, Double> {

	public Square(Value<S, Double> a) {
		super(AlgebraUtils::square, a);
	}

}
