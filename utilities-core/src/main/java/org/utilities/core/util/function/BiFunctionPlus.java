package org.utilities.core.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.utilities.core.util.pair.Pair;

public interface BiFunctionPlus<T, U, R> extends BiFunction<T, U, R> {

	public static <T, U, R> BiFunctionPlus<T, U, R> from(BiFunction<T, U, R> biFunction) {
		return biFunction::apply;
	}

	public static <T, U, R> FunctionPlus<T, R> parseFunction(BiFunction<T, U, ? extends R> biFunction, U u) {
		return t -> biFunction.apply(t, u);
	}

	public static <T, U, R> FunctionPlus<T, R> parseFunction(BiFunction<T, U, ? extends R> biFunction,
			Supplier<? extends U> u) {
		return t -> biFunction.apply(t, u.get());
	}

	public static <T, U, R> FunctionPlus<T, R> parseFunction(BiFunction<T, U, ? extends R> biFunction,
			Function<T, ? extends U> u) {
		return t -> biFunction.apply(t, u.apply(t));
	}

	public static <T, U, R> FunctionPlus<Pair<T, U>, R> parseFunction(BiFunction<T, U, R> biFunction) {
		return pair -> biFunction.apply(pair.getX(), pair.getY());
	}

	default FunctionPlus<Pair<T, U>, R> parseFunction() {
		return BiFunctionPlus.parseFunction(this);
	}

	default FunctionPlus<T, R> parseFunction(U u) {
		return BiFunctionPlus.parseFunction(this, u);
	}

	default FunctionPlus<T, R> parseFunction(Supplier<? extends U> u) {
		return BiFunctionPlus.parseFunction(this, u);
	}

	default FunctionPlus<T, R> parseFunction(Function<T, ? extends U> u) {
		return BiFunctionPlus.parseFunction(this, u);
	}

	public static <T, U, R> BiFunctionPlus<U, T, R> flip(BiFunction<T, U, R> biFunction) {
		return (u, t) -> biFunction.apply(t, u);
	}

	default BiFunctionPlus<U, T, R> flip() {
		return BiFunctionPlus.flip(this);
	}

	public static <T, U, R> BiFunction<T, U, R> applyIfNotNull(BiFunction<T, U, R> biFunction) {
		return (t, u) -> applyIfNotNull(t, u, biFunction);
	}

	public static <T, U, R> R applyIfNotNull(T t, U u, BiFunction<T, U, R> biFunction) {
		return (t != null & u != null) ? biFunction.apply(t, u) : null;
	}

}
