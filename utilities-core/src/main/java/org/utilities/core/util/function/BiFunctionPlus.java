package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.utilities.core.UtilitiesBiFunction;
import org.utilities.core.util.pair.Pair;

public interface BiFunctionPlus<T, U, R> extends BiFunction<T, U, R> {

	default FunctionPlus<Pair<T, U>, R> parseFunction() {
		return UtilitiesBiFunction.parseFunction(this);
	}

	default FunctionPlus<T, R> parseFunction(U u) {
		return UtilitiesBiFunction.parseFunction(this, u);
	}

	default FunctionPlus<T, R> parseFunction(Supplier<? extends U> u) {
		return UtilitiesBiFunction.parseFunction(this, u);
	}

	default FunctionPlus<T, R> parseFunction(Function<T, ? extends U> u) {
		return UtilitiesBiFunction.parseFunction(this, u);
	}

	default BiFunctionPlus<U, T, R> flip() {
		return UtilitiesBiFunction.flip(this);
	}

	default <V> BiFunctionPlus<T, U, V> andThen(Function<? super R, ? extends V> after) {
		return BiFunction.super.andThen(after)::apply;
	}

	// TODO
	default <V> BiFunctionPlus<V, U, R> composeLeft(Function<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return (V v, U u) -> apply(before.apply(v), u);
	}

	default <V, C> BiFunctionPlus<V, U, R> composeLeft(BiFunction<? super V, C, ? extends T> before, C c) {
		Objects.requireNonNull(before);
		return (V v, U u) -> apply(before.apply(v, c), u);
	}

	default <V, C> BiFunctionPlus<V, U, R> composeLeft(BiFunction<? super V, C, ? extends T> before, C c,
			Class<C> type) {
		Objects.requireNonNull(before);
		return (V v, U u) -> apply(before.apply(v, c), u);
	}

	default <V> BiFunctionPlus<T, V, R> composeRight(Function<? super V, ? extends U> before) {
		Objects.requireNonNull(before);
		return (T t, V v) -> apply(t, before.apply(v));
	}

	default <V, C> BiFunctionPlus<T, V, R> composeRight(BiFunction<? super V, C, ? extends U> before, C c) {
		Objects.requireNonNull(before);
		return (T t, V v) -> apply(t, before.apply(v, c));
	}

}
