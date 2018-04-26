package org.utilities.symbolicmath.statistics.rank;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Values;

public class Median<S> extends UnivariateStatistic<S> {

	public Median(Values<S, Double> values) {
		super(new org.apache.commons.math3.stat.descriptive.rank.Median(), values);
	}

}
