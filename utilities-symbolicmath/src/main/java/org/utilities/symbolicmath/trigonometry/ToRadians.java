package org.utilities.symbolicmath.trigonometry;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class ToRadians<S> extends UnaryOperator<S, Double, Double> {

	public ToRadians(Value<S, Double> a) {
		super(Math::toRadians, a);
	}

}
