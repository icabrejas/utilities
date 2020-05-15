package org.utilities.core.util.lambda;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.util.function.BiFunctionPlus;

public class LambdaValue<T> {

	private T value;

	public LambdaValue() {
	}

	public LambdaValue(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public T set(T value) {
		return this.value = value;
	}

	public boolean isPresent() {
		return value != null;
	}

	public T remove() {
		T value = this.value;
		this.value = null;
		return value;
	}

	public T apply(Function<T, T> func) {
		return value = func.apply(value);
	}

	public <U> T apply(BiFunction<T, U, T> func, U u) {
		return apply(BiFunctionPlus.parseFunction(func, u));
	}

	@Override
	public String toString() {
		return "StreamableValue [value=" + value + "]";
	}

}
