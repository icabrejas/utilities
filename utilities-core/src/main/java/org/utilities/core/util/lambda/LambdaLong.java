package org.utilities.core.util.lambda;

import org.utilities.core.lang.UtilitiesNumber;

public class LambdaLong extends LambdaValue<Long> {

	public LambdaLong() {
	}

	public LambdaLong(Long value) {
		super(value);
	}

	public long add(long amount) {
		return set(UtilitiesNumber.sum(get(), amount));
	}

}