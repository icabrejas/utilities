package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public interface DoubleFunctionPlus<R> extends DoubleFunction<R> {

	default <U> DoubleFunctionPlus<U> andThen(Function<R, ? extends U> after) {
		Objects.requireNonNull(after);
		return (double x) -> after.apply(apply(x));
	}

}
