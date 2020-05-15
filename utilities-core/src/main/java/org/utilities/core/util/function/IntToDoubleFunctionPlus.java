package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntToDoubleFunction;

public interface IntToDoubleFunctionPlus extends IntToDoubleFunction {

	default IntToDoubleFunctionPlus andThen(DoubleUnaryOperator after) {
		Objects.requireNonNull(after);
		return (int i) -> after.applyAsDouble(applyAsDouble(i));
	}

	default <R> IntFunctionPlus<R> andThen(Function<Double, ? extends R> after) {
		Objects.requireNonNull(after);
		return (int i) -> after.apply(applyAsDouble(i));
	}

}
