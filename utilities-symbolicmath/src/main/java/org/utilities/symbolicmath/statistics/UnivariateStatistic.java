package org.utilities.symbolicmath.statistics;

import java.util.Collection;

import org.apache.commons.math3.stat.descriptive.AbstractUnivariateStatistic;
import org.utilities.symbolicmath.value.Value;
import org.utilities.symbolicmath.value.Values;

public class UnivariateStatistic<S> implements Value<S, Double> {

	private AbstractUnivariateStatistic statistic;
	private Values<S, Double> values;

	public UnivariateStatistic(AbstractUnivariateStatistic statistic, Values<S, Double> values) {
		this.statistic = statistic;
		this.values = values;
	}

	@Override
	public Double apply(S store) {
		Collection<Double> values = this.values.apply(store);
		return statistic.evaluate(toPrimitive(values));
	}

	private double[] toPrimitive(Collection<Double> values) {
		double[] primitive = new double[values.size()];
		int i = 0;
		for (Double value : values) {
			primitive[i++] = value != null ? value : Double.NaN;
		}
		return primitive;
	}

}
