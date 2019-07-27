package org.utilities.symbolicmath.symbol.array;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.utils.LogicalUtils;
import org.utilities.symbolicmath.utils.array.BooleanArrayUtils;
import org.utilities.symbolicmath.utils.function.ArrayFactory;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;
import org.utilities.symbolicmath.utils.function.FunctionUtils;

public interface BooleanPrimitiveArraySymbol<S> extends Symbol<S, boolean[]> {

	static <S> BooleanPrimitiveArraySymbol<S> symbol(Function<S, boolean[]> func) {
		if (func instanceof BooleanPrimitiveArraySymbol) {
			return (BooleanPrimitiveArraySymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> BooleanPrimitiveArraySymbol<S> constant(boolean[] constant) {
		return store -> constant;
	}

	default <R> ArraySymbol<S, R> andThenAll(Function<Boolean, R> after, ArrayFactory<R> factory) {
		return andThen(FunctionUtils.notNullEval(v -> BooleanArrayUtils.apply(v, after, factory))).asArray();
	}

	default <R> ArraySymbol<S, R> andThenAll(BiFunction<Boolean, Boolean, R> after, Function<S, boolean[]> y,
			ArrayFactory<R> factory) {
		return andThen(BiFunctionUtils.notNullEval((v, w) -> BooleanArrayUtils.apply(v, after, w, factory)), y)
				.asArray();
	}

	default DoublePrimitiveArraySymbol<S> andThenAll(ToDoubleFunction<Boolean> after) {
		return andThen(FunctionUtils.notNullEval(v -> BooleanArrayUtils.apply(v, after))).asDoublePrimitiveArray();
	}

	default DoublePrimitiveArraySymbol<S> andThenAll(ToDoubleBiFunction<Boolean, Boolean> after,
			Function<S, boolean[]> y) {
		return andThen(BiFunctionUtils.notNullEval((v, w) -> BooleanArrayUtils.apply(v, after, w)), y)
				.asDoublePrimitiveArray();
	}

	default BooleanPrimitiveArraySymbol<S> andThenAll(Predicate<Boolean> after) {
		return andThen(FunctionUtils.notNullEval(v -> BooleanArrayUtils.apply(v, after))).asBooleanPrimitiveArray();
	}

	default BooleanPrimitiveArraySymbol<S> andThenAll(BiPredicate<Boolean, Boolean> after, Function<S, boolean[]> y) {
		return andThen(BiFunctionUtils.notNullEval((v, w) -> BooleanArrayUtils.apply(v, after, w)), y)
				.asBooleanPrimitiveArray();
	}

	default BooleanPrimitiveArraySymbol<S> and(Function<S, boolean[]> y) {
		return andThenAll(Boolean::logicalAnd, y);
	}

	default BooleanPrimitiveArraySymbol<S> not() {
		return andThenAll(LogicalUtils::not);
	}

	default BooleanPrimitiveArraySymbol<S> or(Function<S, boolean[]> y) {
		return andThenAll(Boolean::logicalOr, y);
	}

	default BooleanPrimitiveArraySymbol<S> xor(Function<S, boolean[]> y) {
		return andThenAll(Boolean::logicalXor, y);
	}

	default DoublePrimitiveArraySymbol<S> toDouble() {
		return andThenAll(LogicalUtils::parseDouble);
	}

}
