package org.utilities.symbolicmath.statistics.moment;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Value;

public class Mean<S> extends UnivariateStatistic<S> {

	public Mean(Value<S, double[]> values) {
		super(new org.apache.commons.math3.stat.descriptive.moment.Mean(), values);
	}

}
