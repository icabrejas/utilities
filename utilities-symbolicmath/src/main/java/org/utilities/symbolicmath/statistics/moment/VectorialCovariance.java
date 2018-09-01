package org.utilities.symbolicmath.statistics.moment;

import org.apache.commons.math3.linear.RealMatrix;
import org.utilities.symbolicmath.Statistics;
import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class VectorialCovariance<S> extends UnaryOperator<S, double[][], RealMatrix> {

	public VectorialCovariance(Value<S, double[][]> x, boolean biasCorrected) {
		super(x_ -> Statistics.Moment.vectorialCovariance(x_, biasCorrected), x);
	}

}
