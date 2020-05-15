package org.utilities.core.util.lambda;

import org.utilities.core.UtilitiesNumber;

public class LambdaDouble extends LambdaValue<Double> {

	public LambdaDouble() {
	}

	public LambdaDouble(double value) {
		super(value);
	}

	public double min(double x) {
		return apply(UtilitiesNumber::min, x);
	}

	public double max(double x) {
		return apply(UtilitiesNumber::max, x);
	}

}