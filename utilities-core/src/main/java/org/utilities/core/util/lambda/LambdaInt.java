package org.utilities.core.util.lambda;

import org.utilities.core.UtilitiesNumber;

public class LambdaInt extends LambdaValue<Integer> {

	public LambdaInt() {
	}

	public LambdaInt(Integer value) {
		super(value);
	}

	public int increment() {
		return increment(1);
	}

	public int increment(int i) {
		return apply(UtilitiesNumber::sum, i);
	}

}