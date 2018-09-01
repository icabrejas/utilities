package org.utilities.symbolicmath.statistics.correlation;

import org.utilities.symbolicmath.Statistics;
import org.utilities.symbolicmath.operator.BinaryOperator;
import org.utilities.symbolicmath.value.Value;

public class PearsonsCorrelation<S> extends BinaryOperator<S, double[], double[], Double> {

	public PearsonsCorrelation(Value<S, double[]> x, Value<S, double[]> y) {
		super(Statistics.Correlation::pearsonsCorrelation, x, y);
	}

}