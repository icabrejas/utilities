package org.utilities.core.util.lambda;

public class LambdaBoolean extends LambdaValue<Boolean> {

	public LambdaBoolean() {
		super();
	}

	public LambdaBoolean(Boolean value) {
		super(value);
	}

	public boolean negative() {
		return set(!get());
	}

}
