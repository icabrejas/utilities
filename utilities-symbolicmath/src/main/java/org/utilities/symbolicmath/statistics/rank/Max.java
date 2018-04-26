package org.utilities.symbolicmath.statistics.rank;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Values;

public class Max<S> extends UnivariateStatistic<S> {

	public Max(Values<S, Double> values) {
		super(new org.apache.commons.math3.stat.descriptive.rank.Max(), values);
	}

}
