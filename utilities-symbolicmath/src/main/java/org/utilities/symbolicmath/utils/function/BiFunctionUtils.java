package org.utilities.symbolicmath.utils.function;

import java.util.Optional;
import java.util.function.BiFunction;

public class BiFunctionUtils {

	public static <T, U, R> BiFunction<T, U, R> notNullEval(BiFunction<T, U, R> func) {
		return (t, u) -> t == null || u == null ? null : func.apply(t, u);
	}

	public static <T, U, R> BiFunction<Optional<T>, Optional<U>, R> optionalEval(BiFunction<T, U, R> func) {
		return (t, u) -> !t.isPresent() || !u.isPresent() ? null : func.apply(t.get(), u.get());
	}
}
