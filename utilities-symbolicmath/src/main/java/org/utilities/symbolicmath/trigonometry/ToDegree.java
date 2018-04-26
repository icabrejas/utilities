package org.utilities.symbolicmath.trigonometry;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class ToDegree<S> extends UnaryOperator<S, Double, Double> {

	public ToDegree(Value<S, Double> a) {
		super(Math::toDegrees, a);
	}

}
