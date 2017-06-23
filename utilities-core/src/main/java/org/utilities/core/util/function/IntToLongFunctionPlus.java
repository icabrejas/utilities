package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;

public interface IntToLongFunctionPlus extends IntToLongFunction {

	default IntToLongFunctionPlus andThen(LongUnaryOperator after) {
		Objects.requireNonNull(after);
		return (int i) -> after.applyAsLong(applyAsLong(i));
	}

	default <R> IntFunction<R> andThen(LongFunction<? extends R> after) {
		Objects.requireNonNull(after);
		return (int i) -> after.apply(applyAsLong(i));
	}

	default <R> IntFunction<R> andThen(Function<Long, ? extends R> after) {
		Objects.requireNonNull(after);
		return (int i) -> after.apply(applyAsLong(i));
	}

}
