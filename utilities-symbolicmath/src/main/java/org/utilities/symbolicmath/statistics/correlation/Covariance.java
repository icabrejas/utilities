package org.utilities.symbolicmath.statistics.correlation;

import org.utilities.symbolicmath.Statistics;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Covariance<S> extends BinaryOperator<S, double[], double[], Double> {

	public Covariance(Value<S, double[]> x, Value<S, double[]> y, boolean biasCorrected) {
		super((x_, y_) -> Statistics.Correlation.covariance(x_, y_, biasCorrected), x, y);
	}

}