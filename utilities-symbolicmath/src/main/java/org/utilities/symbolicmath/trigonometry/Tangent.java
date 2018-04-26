package org.utilities.symbolicmath.trigonometry;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Tangent<S> extends UnaryOperator<S, Double, Double> {

	public Tangent(Value<S, Double> a) {
		super(Math::tan, a);
	}

}
