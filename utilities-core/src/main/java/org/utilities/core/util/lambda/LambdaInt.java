package org.utilities.core.util.lambda;

import org.utilities.core.lang.UtilitiesNumber;

public class LambdaInt extends LambdaValue<Integer> {

	public LambdaInt() {
	}

	public LambdaInt(Integer value) {
		super(value);
	}

	public int add(int i) {
		return set(UtilitiesNumber.sum(get(), i));
	}

}