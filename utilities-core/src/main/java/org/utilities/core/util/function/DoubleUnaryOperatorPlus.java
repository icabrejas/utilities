package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public interface DoubleUnaryOperatorPlus extends DoubleUnaryOperator {

	default DoubleUnaryOperatorPlus andThen(DoubleUnaryOperator after) {
		return DoubleUnaryOperator.super.andThen(after)::applyAsDouble;
	}

	default <R> DoubleFunctionPlus<R> andThen(DoubleFunction<? extends R> after) {
		Objects.requireNonNull(after);
		return (double x) -> after.apply(applyAsDouble(x));
	}

	default <R> DoubleFunctionPlus<R> andThen(Function<Double, ? extends R> after) {
		Objects.requireNonNull(after);
		return (double x) -> after.apply(applyAsDouble(x));
	}

	static DoubleUnaryOperatorPlus inverse() {
		return t -> 1. / t;
	}

	static DoubleUnaryOperatorPlus sqrt() {
		return Math::sqrt;
	}

	static DoubleUnaryOperatorPlus square() {
		return x -> x * x;
	}

}
