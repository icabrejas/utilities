package org.utilities.core.util.lambda;

import org.utilities.core.UtilitiesNumber;

public class LambdaLong extends LambdaValue<Long> {

	public LambdaLong() {
	}

	public LambdaLong(Long value) {
		super(value);
	}

	public long increment() {
		return increment(1);
	}

	public long increment(long amount) {
		return apply(UtilitiesNumber::sum, amount);
	}

}