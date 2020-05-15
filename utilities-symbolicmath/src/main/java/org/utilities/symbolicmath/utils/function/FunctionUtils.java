package org.utilities.symbolicmath.utils.function;

import java.util.Optional;
import java.util.function.Function;

public class FunctionUtils {

	public static <T, R> Function<T, R> notNullEval(Function<T, R> func) {
		return t -> t == null ? null : func.apply(t);
	}

	public static <T, R> Function<Optional<T>, R> optionalEval(Function<T, R> func) {
		return t -> !t.isPresent() ? null : func.apply(t.get());
	}

}
