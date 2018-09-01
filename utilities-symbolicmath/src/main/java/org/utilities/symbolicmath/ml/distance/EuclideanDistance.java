package org.utilities.symbolicmath.ml.distance;

import org.utilities.symbolicmath.Distance;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class EuclideanDistance<S> extends BinaryOperator<S, double[], double[], Double> {

	public EuclideanDistance(Value<S, double[]> x, Value<S, double[]> y) {
		super(Distance::euclideanDistance, x, y);
	}

}
