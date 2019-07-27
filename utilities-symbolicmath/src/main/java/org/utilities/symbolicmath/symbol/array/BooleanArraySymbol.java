package org.utilities.symbolicmath.symbol.array;

import java.util.function.Function;

import org.utilities.symbolicmath.utils.LogicalUtils;
import org.utilities.symbolicmath.utils.function.ArrayFactory;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;
import org.utilities.symbolicmath.utils.function.BooleanBiFunction;
import org.utilities.symbolicmath.utils.function.BooleanFunction;
import org.utilities.symbolicmath.utils.function.FunctionUtils;

public interface BooleanArraySymbol<S> extends ArraySymbol<S, Boolean> {

	static <S> BooleanArraySymbol<S> symbol(Function<S, Boolean[]> func) {
		if (func instanceof BooleanArraySymbol) {
			return (BooleanArraySymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> BooleanArraySymbol<S> constant(Boolean[] constant) {
		return store -> constant;
	}

	default <R> ArraySymbol<S, R> andThenAllNotNullEval(BooleanFunction<R> after, ArrayFactory<R> factory) {
		return andThenAll(FunctionUtils.notNullEval(after::apply), factory);
	}

	default <R> ArraySymbol<S, R> andThenAllNotNullEval(BooleanBiFunction<R> after, Function<S, Boolean[]> y,
			ArrayFactory<R> factory) {
		return andThenAll(BiFunctionUtils.notNullEval(after::apply), y, factory);
	}

	default BooleanArraySymbol<S> andThenAllNotNullEval(BooleanFunction<Boolean> after) {
		return andThenAll(FunctionUtils.notNullEval(after::apply), Boolean[]::new).asBooleanArray();
	}

	default BooleanArraySymbol<S> andThenAllNotNullEval(BooleanBiFunction<Boolean> after, Function<S, Boolean[]> y) {
		return andThenAll(BiFunctionUtils.notNullEval(after::apply), y, Boolean[]::new).asBooleanArray();
	}

	default BooleanArraySymbol<S> and(Function<S, Boolean[]> y) {
		return andThenAllNotNullEval(Boolean::logicalAnd, y);
	}

	default BooleanArraySymbol<S> not() {
		return andThenAllNotNullEval(LogicalUtils::not);
	}

	default BooleanArraySymbol<S> or(Function<S, Boolean[]> y) {
		return andThenAllNotNullEval(Boolean::logicalOr, y);
	}

	default BooleanArraySymbol<S> xor(Function<S, Boolean[]> y) {
		return andThenAllNotNullEval(Boolean::logicalXor, y);
	}

	default DoubleArraySymbol<S> toDouble() {
		return andThenAllNotNullEval(LogicalUtils::parseDouble, Double[]::new).asDoubleArray();
	}

	default BooleanPrimitiveArraySymbol<S> toPrimitive() {
		return andThenAll(Boolean::booleanValue);
	}

}
