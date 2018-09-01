package org.utilities.symbolicmath.statistics;

import org.apache.commons.math3.stat.descriptive.AbstractUnivariateStatistic;
import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class UnivariateStatistic<S> extends UnaryOperator<S, double[], Double> {

	public UnivariateStatistic(AbstractUnivariateStatistic statistic, Value<S, double[]> x) {
		super(statistic::evaluate, x);
	}

}
