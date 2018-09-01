package org.utilities.symbolicmath.operator;

import java.util.Collection;

import org.utilities.symbolicmath.value.Value;

public class ToPrimitive<S, C extends Collection<Double>> extends UnaryOperator<S, C, double[]> {

	public ToPrimitive(Value<S, C> x) {
		super(ToPrimitive::toPrimitive, x);
	}

	private static double[] toPrimitive(Collection<Double> values) {
		double[] primitive = new double[values.size()];
		int i = 0;
		for (Double value : values) {
			primitive[i++] = value != null ? value : Double.NaN;
		}
		return primitive;
	}
}
