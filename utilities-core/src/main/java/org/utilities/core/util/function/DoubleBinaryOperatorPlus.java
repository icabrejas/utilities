package org.utilities.core.util.function;

import java.util.function.DoubleBinaryOperator;

public interface DoubleBinaryOperatorPlus extends DoubleBinaryOperator {

	public static DoubleUnaryOperatorPlus parseUnary(DoubleBinaryOperator biFunction, double u) {
		return t -> biFunction.applyAsDouble(t, u);
	}

}
