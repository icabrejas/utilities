package org.utilities.symbolicmath.statistics.correlation;

import org.utilities.symbolicmath.Statistics;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class StorelessCovariance<S> extends BinaryOperator<S, double[], double[], Double> {

	public StorelessCovariance(Value<S, double[]> x, Value<S, double[]> y, int dim, boolean biasCorrected) {
		super((x_, y_) -> Statistics.Correlation.storelessCovariance(x_, y_, dim, biasCorrected), x, y);
	}

}