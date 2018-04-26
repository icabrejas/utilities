package org.utilities.symbolicmath.trigonometry;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Arccosine<S> extends UnaryOperator<S, Double, Double> {

	public Arccosine(Value<S, Double> a) {
		super(Math::acos, a);
	}

}
