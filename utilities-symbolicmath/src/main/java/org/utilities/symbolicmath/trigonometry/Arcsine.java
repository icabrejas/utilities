package org.utilities.symbolicmath.trigonometry;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Arcsine<S> extends UnaryOperator<S, Double, Double> {

	public Arcsine(Value<S, Double> a) {
		super(Math::asin, a);
	}

}
