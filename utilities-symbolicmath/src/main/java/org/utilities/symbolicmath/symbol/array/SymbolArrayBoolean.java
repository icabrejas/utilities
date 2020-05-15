package org.utilities.symbolicmath.symbol.array;

import java.util.function.Function;

import org.utilities.core.util.function.ArrayFactory;
import org.utilities.core.util.function.BooleanBiFunctionPlus;
import org.utilities.core.util.function.BooleanFunctionPlus;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.SymbolBoolean;
import org.utilities.symbolicmath.utils.LogicalUtils;

public interface SymbolArrayBoolean<S> extends SymbolArray<S, Boolean> {

	static <S> SymbolArrayBoolean<S> empty() {
		return constant(null);
	}

	static <S> SymbolArrayBoolean<S> constant(Boolean[] constant) {
		return s -> constant;
	}

	static <S> SymbolArrayBoolean<S> as(Function<S, Boolean[]> func) {
		if (func instanceof SymbolArrayBoolean) {
			return (SymbolArrayBoolean<S>) func;
		} else {
			return func::apply;
		}
	}

	default <R> SymbolArray<S, R> andThenForeachNotNullEval(BooleanFunctionPlus<R> after, ArrayFactory<R> factory) {
		return andThenForeachNotNullEval(after::apply, factory);
	}

	default <R> SymbolArray<S, R> andThenForeachNotNullEval(BooleanBiFunctionPlus<R> after, Symbol<S, Boolean[]> y,
			ArrayFactory<R> factory) {
		return andThenForeachNotNullEval(after::apply, y, factory);
	}

	default SymbolArrayBoolean<S> andThenForeachNotNullEval(BooleanFunctionPlus<Boolean> after) {
		return as(andThenForeachNotNullEval(after::apply, Boolean[]::new));
	}

	default SymbolArrayBoolean<S> andThenForeachNotNullEval(BooleanBiFunctionPlus<Boolean> after,
			Symbol<S, Boolean[]> y) {
		return as(andThenForeachNotNullEval(after::apply, y, Boolean[]::new));
	}

	default SymbolArrayBoolean<S> and(Symbol<S, Boolean[]> y) {
		return andThenForeachNotNullEval(Boolean::logicalAnd, y);
	}

	default SymbolArrayBoolean<S> not() {
		return andThenForeachNotNullEval(LogicalUtils::not);
	}

	default SymbolArrayBoolean<S> or(Symbol<S, Boolean[]> y) {
		return andThenForeachNotNullEval(Boolean::logicalOr, y);
	}

	default SymbolArrayBoolean<S> xor(Symbol<S, Boolean[]> y) {
		return andThenForeachNotNullEval(Boolean::logicalXor, y);
	}

	default SymbolArrayDouble<S> toDouble() {
		return SymbolArrayDouble.as(andThenForeachNotNullEval(LogicalUtils::parseDouble, Double[]::new));
	}

	default SymbolArrayBoolean<S> nullOmit() {
		return as(filter(isNotNullForeach(), Boolean[]::new));
	}

	default SymbolArrayBooleanPrimitive<S> toPrimitive(boolean nullOmit) {
		if (nullOmit) {
			return this.nullOmit()
					.andThenForeach(Boolean::booleanValue);
		} else {
			return this.containsNull()
					.ifElse(SymbolArrayBooleanPrimitive.constant(null), andThenForeach(Boolean::booleanValue))
					.apply(SymbolArrayBooleanPrimitive::as);
		}
	}

	default SymbolBoolean<S> any() {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::any));
	}

	default SymbolBoolean<S> all() {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::all));
	}

}
