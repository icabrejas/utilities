package org.utilities.symbolicmath.statistics.summary;

import org.utilities.symbolicmath.statistics.UnivariateStatistic;
import org.utilities.symbolicmath.value.Values;

public class Product<S> extends UnivariateStatistic<S> {

	public Product(Values<S, Double> values) {
		super(new org.apache.commons.math3.stat.descriptive.summary.Product(), values);
	}

}
