package org.utilities.symbolicmath.trigonometry;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Cosine<S> extends UnaryOperator<S, Double, Double> {

	public Cosine(Value<S, Double> a) {
		super(Math::cos, a);
	}

}
