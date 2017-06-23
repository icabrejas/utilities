package org.utilities.core.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Pipeline<T> {

	private T t;

	public Pipeline(T t) {
		this.t = t;
	}

	public static <T> Pipeline<T> newInstance(T t) {
		return new Pipeline<T>(t);
	}

	public <U, R> Pipeline<R> apply(BiFunction<T, U, R> bifunction, U u) {
		return apply(BiFunctionPlus.parseFunction(bifunction, u));
	}

	public <R> Pipeline<R> apply(Function<T, R> bifunction) {
		return new Pipeline<R>(bifunction.apply(t));
	}

	public T get() {
		return t;
	}

}
