package org.utilities.symbolicmath.statistics.summary;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Value;

public class Sum<S> extends UnivariateStatistic<S> {

	public Sum(Value<S, double[]> values) {
		super(new org.apache.commons.math3.stat.descriptive.summary.Sum(), values);
	}

}
