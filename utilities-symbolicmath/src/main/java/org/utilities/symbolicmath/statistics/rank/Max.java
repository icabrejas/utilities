package org.utilities.symbolicmath.statistics.rank;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Value;

public class Max<S> extends UnivariateStatistic<S> {

	public Max(Value<S, double[]> values) {
		super(new org.apache.commons.math3.stat.descriptive.rank.Max(), values);
	}

}
