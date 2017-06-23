package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

public interface IntFunctionPlus<R> extends IntFunction<R> {

	default <U> IntFunctionPlus<U> andThen(Function<R, ? extends U> after) {
		Objects.requireNonNull(after);
		return (int i) -> after.apply(apply(i));
	}

}
