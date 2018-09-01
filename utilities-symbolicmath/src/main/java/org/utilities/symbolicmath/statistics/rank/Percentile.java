package org.utilities.symbolicmath.statistics.rank;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Value;

public class Percentile<S> extends UnivariateStatistic<S> {

	public Percentile(Value<S, double[]> values, double quantile) {
		super(new org.apache.commons.math3.stat.descriptive.rank.Percentile(quantile), values);
	}

}
