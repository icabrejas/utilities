package org.utilities.symbolicmath.statistics.rank;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Value;

public class Median<S> extends UnivariateStatistic<S> {

	public Median(Value<S, double[]> values) {
		super(new org.apache.commons.math3.stat.descriptive.rank.Median(), values);
	}

}
