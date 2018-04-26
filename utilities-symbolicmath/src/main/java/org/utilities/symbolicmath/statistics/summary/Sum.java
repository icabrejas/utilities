package org.utilities.symbolicmath.statistics.summary;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Values;

public class Sum<S> extends UnivariateStatistic<S> {

	public Sum(Values<S, Double> values) {
		super(new org.apache.commons.math3.stat.descriptive.summary.Sum(), values);
	}

}
