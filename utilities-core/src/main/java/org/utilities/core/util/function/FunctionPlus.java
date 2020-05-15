package org.utilities.core.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.utilities.core.UtilitiesFunction;

public interface FunctionPlus<T, R> extends Function<T, R> {

	@Override
	default <V> FunctionPlus<T, V> andThen(Function<? super R, ? extends V> after) {
		return Function.super.andThen(after)::apply;
	}

	default <U, V> FunctionPlus<T, V> andThen(BiFunctionPlus<? super R, ? super U, ? extends V> after, U u) {
		return andThen(after.parseFunction(u));
	}

	default <U, V> FunctionPlus<T, V> andThen(BiFunction<R, U, V> after, U u) {
		return andThen(BiFunctionPlus.parseFunction(after, u));
	}

	default <U, V> Function<T, R> ifElse(Predicate<T> expression, Function<T, R> if_, Function<T, R> else_) {
		return t -> {
			if (expression.test(t)) {
				return if_.apply(t);
			} else {
				return else_.apply(t);
			}
		};
	}

	@Override
	default <V> FunctionPlus<V, R> compose(Function<? super V, ? extends T> before) {
		return Function.super.compose(before)::apply;
	}

	default <U, V> Function<U, R> compose(BiFunctionPlus<? super U, ? super V, ? extends T> before, V v) {
		return compose(before.parseFunction(v));
	}

	default SupplierPlus<R> parseSupplier(T t) {
		return UtilitiesFunction.parseSupplier(this, t)::get;
	}

}
