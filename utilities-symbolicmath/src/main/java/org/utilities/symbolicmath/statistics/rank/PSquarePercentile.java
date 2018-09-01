package org.utilities.symbolicmath.statistics.rank;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Value;

public class PSquarePercentile<S> extends UnivariateStatistic<S> {

	public PSquarePercentile(Value<S, double[]> values, double p) {
		super(new org.apache.commons.math3.stat.descriptive.rank.PSquarePercentile(p), values);
	}

}
