package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public interface IntUnaryOperatorPlus extends IntUnaryOperator {

	default IntUnaryOperatorPlus andThen(IntUnaryOperator after) {
		return IntUnaryOperator.super.andThen(after)::applyAsInt;
	}

	default <R> IntFunctionPlus<R> andThen(IntFunction<? extends R> after) {
		Objects.requireNonNull(after);
		return (int i) -> after.apply(applyAsInt(i));
	}

	default <R> IntFunctionPlus<R> andThen(Function<Integer, ? extends R> after) {
		Objects.requireNonNull(after);
		return (int i) -> after.apply(applyAsInt(i));
	}

}
