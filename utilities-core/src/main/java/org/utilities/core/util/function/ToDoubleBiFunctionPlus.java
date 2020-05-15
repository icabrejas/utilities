package org.utilities.core.util.function;

import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

public interface ToDoubleBiFunctionPlus<T, U> extends ToDoubleBiFunction<T, U> {

	public static <T, U> ToDoubleFunction<T> parseToDoubleFunction(ToDoubleBiFunction<T, U> biFunction, U u) {
		return t -> biFunction.applyAsDouble(t, u);
	}

	public static <T, U> FunctionPlus<T, Double> parseFunction(ToDoubleBiFunction<T, U> biFunction, U u) {
		return t -> biFunction.applyAsDouble(t, u);
	}

}
