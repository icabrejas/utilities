package org.utilities.symbolicmath.statistics.summary;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Value;

public class Product<S> extends UnivariateStatistic<S> {

	public Product(Value<S, double[]> values) {
		super(new org.apache.commons.math3.stat.descriptive.summary.Product(), values);
	}

}
