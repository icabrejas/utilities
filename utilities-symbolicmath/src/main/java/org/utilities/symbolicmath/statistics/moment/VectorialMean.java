package org.utilities.symbolicmath.statistics.moment;

import org.utilities.symbolicmath.Statistics;
import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class VectorialMean<S> extends UnaryOperator<S, double[][], double[]> {

	public VectorialMean(Value<S, double[][]> x) {
		super(Statistics.Moment::vectorialMean, x);
	}

}
