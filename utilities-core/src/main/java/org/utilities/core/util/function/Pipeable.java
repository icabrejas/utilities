package org.utilities.core.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Pipeable<T> {

	@SuppressWarnings("unchecked")
	default <R> R apply(Function<T, R> func) {
		return func.apply((T) this);
	}

	@SuppressWarnings("unchecked")
	public default <U, R> R apply(BiFunction<T, U, R> func, U u) {
		return func.apply((T) this, u);
	}

	@SuppressWarnings("unchecked")
	public default Pipeline<T> pipe() {
		return Pipeline.newInstance((T) this);
	}

}
