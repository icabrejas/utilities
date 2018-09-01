package org.utilities.symbolicmath.statistics.summary;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Value;

public class SumOfSquares<S> extends UnivariateStatistic<S> {

	public SumOfSquares(Value<S, double[]> values) {
		super(new org.apache.commons.math3.stat.descriptive.summary.SumOfSquares(), values);
	}

}
