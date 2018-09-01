package org.utilities.symbolicmath.statistics.correlation;

import org.utilities.symbolicmath.Statistics;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class SpearmansCorrelation<S> extends BinaryOperator<S, double[], double[], Double> {

	public SpearmansCorrelation(Value<S, double[]> x, Value<S, double[]> y) {
		super(Statistics.Correlation::spearmansCorrelation, x, y);
	}

}