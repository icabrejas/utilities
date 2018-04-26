package org.utilities.symbolicmath.trigonometry;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Arctangent<S> extends UnaryOperator<S, Double, Double> {

	public Arctangent(Value<S, Double> a) {
		super(Math::atan, a);
	}

}
