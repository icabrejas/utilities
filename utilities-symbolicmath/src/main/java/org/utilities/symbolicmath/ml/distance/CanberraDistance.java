package org.utilities.symbolicmath.ml.distance;

import org.utilities.symbolicmath.Distance;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class CanberraDistance<S> extends BinaryOperator<S, double[], double[], Double> {

	public CanberraDistance(Value<S, double[]> x, Value<S, double[]> y) {
		super(Distance::canberraDistance, x, y);
	}

}