package org.utilities.symbolicmath.statistics.moment;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Values;

public class Kurtosis<S> extends UnivariateStatistic<S> {

	public Kurtosis(Values<S, Double> values) {
		super(new org.apache.commons.math3.stat.descriptive.moment.Kurtosis(), values);
	}

}
