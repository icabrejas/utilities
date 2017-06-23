package org.utilities.core.util.lambda;

import org.utilities.core.lang.UtilitiesNumber;

public class LambdaDouble extends LambdaValue<Double> {

	public LambdaDouble() {
	}

	public LambdaDouble(double value) {
		super(value);
	}

	public double min(double x) {
		return set(UtilitiesNumber.min(get(), x));
	}

	public double max(double x) {
		return set(UtilitiesNumber.max(get(), x));
	}

}