package org.utilities.symbolicmath.trigonometry;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Sine<S> extends UnaryOperator<S, Double, Double> {

	public Sine(Value<S, Double> a) {
		super(Math::sin, a);
	}

}
