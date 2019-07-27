package org.utilities.symbolicmath.utils.function;

import java.util.Optional;
import java.util.function.Function;

public class FunctionUtils {

	public static <T, R> Function<T, R> notNullEval(Function<T, R> func) {
		return t -> t != null ? func.apply(t) : null;
	}

	public static <T, R> Function<Optional<T>, R> optionalEval(Function<T, R> func) {
		return t -> t.isPresent() ? func.apply(t.get()) : null;
	}

}
