
package org.utilities.symbolicmath.symbol.array;

import java.util.Arrays;
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
import org.utilities.symbolicmath.symbol.list.SymbolList;
import org.utilities.symbolicmath.utils.ObjectUtils;

public interface SymbolArray<S, V> extends Symbol<S, V[]> {

	static <S, V> SymbolArray<S, V> empty() {
		return constant(null);
	}

	static <S, V> SymbolArray<S, V> constant(V[] constant) {
		return s -> constant;
	}

	static <S, V> SymbolArray<S, V> as(Function<S, V[]> func) {
		if (func instanceof SymbolArray) {
			return (SymbolArray<S, V>) func;
		} else {
			return func::apply;
		}
	}

	default <R> SymbolArray<S, R> andThenForeach(Function<V, R> after, ArrayFactory<R> factory) {
		return as(andThenNotNullEval(v -> UtilitiesArray.mapAll(v, after, factory)));
	}

	default <W, R> SymbolArray<S, R> andThenForeach(BiFunction<V, W, R> after, ArrayFactory<R> factory,
			Symbol<S, W[]> y) {
		return as(andThenNotNullEval((v, w) -> UtilitiesArray.mapAll(v, w, after, factory), y));
	}

	default SymbolArrayDoublePrimitive<S> andThenForeach(ToDoubleFunction<V> after) {
		return SymbolArrayDoublePrimitive.as(andThenNotNullEval(v -> UtilitiesArray.mapAll(v, after)));
	}

	default <W> SymbolArrayDoublePrimitive<S> andThenForeach(ToDoubleBiFunction<V, W> after, Symbol<S, W[]> y) {
		return SymbolArrayDoublePrimitive.as(andThenNotNullEval((v, w) -> UtilitiesArray.mapAll(v, w, after), y));
	}

	default SymbolArrayBooleanPrimitive<S> andThenForeach(Predicate<V> after) {
		return SymbolArrayBooleanPrimitive.as(andThenNotNullEval(v -> UtilitiesArray.mapAll(v, after)));
	}

	default SymbolArrayBooleanPrimitive<S> isNotNullForeach() {
		return andThenForeach(ObjectUtils::isNotNull);
	}

	default SymbolArrayBooleanPrimitive<S> isNullForeach() {
		return andThenForeach(ObjectUtils::isNull);
	}

	default <W> SymbolArrayBooleanPrimitive<S> andThenForeach(BiPredicate<V, W> after, Symbol<S, W[]> y) {
		return SymbolArrayBooleanPrimitive.as(andThenNotNullEval((v, w) -> UtilitiesArray.mapAll(v, w, after), y));
	}

	// TODO
	// default SymbolArrayBooleanPrimitive<S> isEqualsForeach(Symbol<S, V> y) {
	// return andThenForeach(ObjectUtils::areEquals, y);
	// }

	// TODO
	// default SymbolArrayBooleanPrimitive<S> isNotEqualsForeach(Symbol<S, V> y)
	// {
	// return andThenForeach(ObjectUtils::areNotEquals, y);
	// }

	default SymbolList<S, V> toList() {
		return SymbolList.as(andThenNotNullEval(Arrays::asList));
	}

	default SymbolBoolean<S> containsNull() {
		// FIXME isNullForeach calculate all booleans
		return isNullForeach().any();
	}

	default SymbolArray<S, V> filter(SymbolArrayBooleanPrimitive<S> filter, ArrayFactory<V> factory) {
		return as(s -> UtilitiesArray.filter(this.apply(s), filter.apply(s), factory));
	}

	default SymbolArray<S, V> nullOmit(ArrayFactory<V> factory) {
		return filter(isNotNullForeach(), factory);
	}

}
