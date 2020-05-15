package org.utilities.symbolicmath.symbol.array;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

import org.utilities.core.UtilitiesArray;
import org.utilities.core.util.function.ArrayFactory;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.SymbolBoolean;
import org.utilities.symbolicmath.utils.LogicalUtils;

public interface SymbolArrayBooleanPrimitive<S> extends Symbol<S, boolean[]> {

	static <S> SymbolArrayBooleanPrimitive<S> empty() {
		return constant(null);
	}

	static <S> SymbolArrayBooleanPrimitive<S> constant(boolean[] constant) {
		return s -> constant;
	}

	static <S> SymbolArrayBooleanPrimitive<S> as(Function<S, boolean[]> func) {
		if (func instanceof SymbolArrayBooleanPrimitive) {
			return (SymbolArrayBooleanPrimitive<S>) func;
		} else {
			return func::apply;
		}
	}

	default <R> SymbolArray<S, R> andThenForeach(Function<Boolean, R> after, ArrayFactory<R> factory) {
		return SymbolArray.as(andThenNotNullEval(v -> UtilitiesArray.mapAll(v, after, factory)));
	}

	default <R> SymbolArray<S, R> andThenForeach(BiFunction<Boolean, Boolean, R> after, Symbol<S, boolean[]> y,
			ArrayFactory<R> factory) {
		return SymbolArray.as(andThenNotNullEval((v, w) -> UtilitiesArray.mapAll(v, w, after, factory), y));
	}

	default SymbolArrayDoublePrimitive<S> andThenForeach(ToDoubleFunction<Boolean> after) {
		return SymbolArrayDoublePrimitive.as(andThenNotNullEval(v -> UtilitiesArray.mapAll(v, after)));
	}

	default SymbolArrayDoublePrimitive<S> andThenForeach(ToDoubleBiFunction<Boolean, Boolean> after,
			Symbol<S, boolean[]> y) {
		return SymbolArrayDoublePrimitive.as(andThenNotNullEval((v, w) -> UtilitiesArray.mapAll(v, w, after), y));
	}

	default SymbolArrayBooleanPrimitive<S> andThenForeach(Predicate<Boolean> after) {
		return as(andThenNotNullEval(v -> UtilitiesArray.mapAll(v, after)));
	}

	default SymbolArrayBooleanPrimitive<S> andThenForeach(BiPredicate<Boolean, Boolean> after, Symbol<S, boolean[]> y) {
		return as(andThenNotNullEval((v, w) -> UtilitiesArray.mapAll(v, w, after), y));
	}

	default SymbolArrayBooleanPrimitive<S> and(Symbol<S, boolean[]> y) {
		return andThenForeach(Boolean::logicalAnd, y);
	}

	default SymbolArrayBooleanPrimitive<S> not() {
		return andThenForeach(LogicalUtils::not);
	}

	default SymbolArrayBooleanPrimitive<S> or(Symbol<S, boolean[]> y) {
		return andThenForeach(Boolean::logicalOr, y);
	}

	default SymbolArrayBooleanPrimitive<S> xor(Symbol<S, boolean[]> y) {
		return andThenForeach(Boolean::logicalXor, y);
	}

	default SymbolArrayDoublePrimitive<S> toDouble() {
		return andThenForeach(LogicalUtils::parseDouble);
	}

	default SymbolBoolean<S> any() {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::any));
	}

	default SymbolBoolean<S> all() {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::all));
	}

}
