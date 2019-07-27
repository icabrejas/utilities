package org.utilities.symbolicmath.symbol.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.list.ListSymbol;
import org.utilities.symbolicmath.utils.ObjectUtils;
import org.utilities.symbolicmath.utils.array.ArrayUtils;
import org.utilities.symbolicmath.utils.function.ArrayFactory;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;
import org.utilities.symbolicmath.utils.function.FunctionUtils;

public interface ArraySymbol<S, V> extends Symbol<S, V[]> {

	static <S, V> ArraySymbol<S, V> symbol(Function<S, V[]> func) {
		if (func instanceof ArraySymbol) {
			return (ArraySymbol<S, V>) func;
		} else {
			return func::apply;
		}
	}

	static <S, V> ArraySymbol<S, V> constant(V[] constant) {
		return store -> constant;
	}

	default <R> ArraySymbol<S, R> andThenAll(Function<V, R> after, ArrayFactory<R> factory) {
		return andThen(FunctionUtils.notNullEval(v -> ArrayUtils.apply(v, after, factory))).asArray();
	}

	default <W, R> ArraySymbol<S, R> andThenAll(BiFunction<V, W, R> after, Function<S, W[]> y,
			ArrayFactory<R> factory) {
		return andThen(BiFunctionUtils.notNullEval((v, w) -> ArrayUtils.apply(v, after, w, factory)), y).asArray();
	}

	default DoublePrimitiveArraySymbol<S> andThenAll(ToDoubleFunction<V> after) {
		return andThen(FunctionUtils.notNullEval(v -> ArrayUtils.apply(v, after))).asDoublePrimitiveArray();
	}

	default <W> DoublePrimitiveArraySymbol<S> andThenAll(ToDoubleBiFunction<V, W> after, Function<S, W[]> y) {
		return andThen(BiFunctionUtils.notNullEval((v, w) -> ArrayUtils.apply(v, after, w)), y)
				.asDoublePrimitiveArray();
	}

	default BooleanPrimitiveArraySymbol<S> andThenAll(Predicate<V> after) {
		return andThen(FunctionUtils.notNullEval(v -> ArrayUtils.apply(v, after))).asBooleanPrimitiveArray();
	}

	default <W> BooleanPrimitiveArraySymbol<S> andThenAll(BiPredicate<V, W> after, Function<S, W[]> y) {
		return andThen(BiFunctionUtils.notNullEval((v, w) -> ArrayUtils.apply(v, after, w)), y)
				.asBooleanPrimitiveArray();
	}

	default ListSymbol<S, V> toList() {
		return andThen(FunctionUtils.notNullEval((Function<V[], List<V>>) Arrays::asList)).asList();
	}

	default BooleanPrimitiveArraySymbol<S> isNotNull() {
		return andThenAll(ObjectUtils::isNotNull);
	}

	default BooleanPrimitiveArraySymbol<S> isNull() {
		return andThenAll(ObjectUtils::isNull);
	}

	default ArraySymbol<S, V> filter(BooleanPrimitiveArraySymbol<S> filter, ArrayFactory<V> factory) {
		return store -> ArrayUtils.filter(this.apply(store), filter.apply(store), factory);
	}

	default ArraySymbol<S, V> nullOmit(ArrayFactory<V> factory) {
		return filter(isNotNull(), factory);
	}

}
