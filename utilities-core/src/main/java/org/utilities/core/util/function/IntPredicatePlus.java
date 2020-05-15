package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntPredicate;

public interface IntPredicatePlus extends IntPredicate {

	default <R> IntFunctionPlus<R> andThen(Function<Boolean, R> before) {
		Objects.requireNonNull(before);
		return v -> before.apply(test(v));
	}

}
