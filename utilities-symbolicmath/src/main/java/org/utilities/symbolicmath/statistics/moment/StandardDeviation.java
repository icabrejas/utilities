package org.utilities.symbolicmath.statistics.moment;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Values;

public class StandardDeviation<S> extends UnivariateStatistic<S> {

	public StandardDeviation(Values<S, Double> values) {
		super(new org.apache.commons.math3.stat.descriptive.moment.StandardDeviation(), values);
	}

}
