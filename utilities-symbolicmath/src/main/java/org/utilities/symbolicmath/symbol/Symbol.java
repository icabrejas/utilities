package org.utilities.symbolicmath.symbol;

import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.symbolicmath.symbol.array.ArraySymbol;
import org.utilities.symbolicmath.symbol.array.BooleanArraySymbol;
import org.utilities.symbolicmath.symbol.array.BooleanPrimitiveArraySymbol;
import org.utilities.symbolicmath.symbol.array.DoubleArraySymbol;
import org.utilities.symbolicmath.symbol.array.DoublePrimitiveArray2Symbol;
import org.utilities.symbolicmath.symbol.array.DoublePrimitiveArraySymbol;
import org.utilities.symbolicmath.symbol.list.BooleanListSymbol;
import org.utilities.symbolicmath.symbol.list.DoubleListSymbol;
import org.utilities.symbolicmath.symbol.list.ListSymbol;
import org.utilities.symbolicmath.utils.ObjectUtils;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;

public interface Symbol<S, V> extends Function<S, V> {

	static <S, V> Symbol<S, V> empty() {
		return constant(null);
	}

	static <S, V> Symbol<S, V> constant(V constant) {
		return store -> constant;
	}

	static <S, V> Symbol<S, V> symbol(Function<S, V> func) {
		if (func instanceof Symbol) {
			return (Symbol<S, V>) func;
		} else {
			return func::apply;
		}
	}

	@SuppressWarnings("unchecked")
	default BooleanSymbol<S> asBoolean() {
		return BooleanSymbol.symbol((Symbol<S, Boolean>) this);
	}

	@SuppressWarnings("unchecked")
	default DoubleSymbol<S> asDouble() {
		return DoubleSymbol.symbol((Symbol<S, Double>) this);
	}

	@SuppressWarnings("unchecked")
	default FloatSymbol<S> asFloat() {
		return FloatSymbol.symbol((Symbol<S, Float>) this);
	}

	@SuppressWarnings("unchecked")
	default IntSymbol<S> asInt() {
		return IntSymbol.symbol((Symbol<S, Integer>) this);
	}

	@SuppressWarnings("unchecked")
	default LongSymbol<S> asLong() {
		return LongSymbol.symbol((Symbol<S, Long>) this);
	}

	@SuppressWarnings("unchecked")
	default StringSymbol<S> asString() {
		return StringSymbol.symbol((Symbol<S, String>) this);
	}

	@SuppressWarnings("unchecked")
	default InstantSymbol<S> asInstant() {
		return InstantSymbol.symbol((Symbol<S, Instant>) this);
	}

	@SuppressWarnings("unchecked")
	default <R> ListSymbol<S, R> asList() {
		return ListSymbol.symbol((Symbol<S, List<R>>) this);
	}

	@SuppressWarnings("unchecked")
	default BooleanListSymbol<S> asBooleanList() {
		return BooleanListSymbol.symbol((Symbol<S, List<Boolean>>) this);
	}

	@SuppressWarnings("unchecked")
	default DoubleListSymbol<S> asDoubleList() {
		return DoubleListSymbol.symbol((Symbol<S, List<Double>>) this);
	}

	@SuppressWarnings("unchecked")
	default <R> ArraySymbol<S, R> asArray() {
		return ArraySymbol.symbol((Symbol<S, R[]>) this);
	}

	@SuppressWarnings("unchecked")
	default BooleanArraySymbol<S> asBooleanArray() {
		return BooleanArraySymbol.symbol((Symbol<S, Boolean[]>) this);
	}

	@SuppressWarnings("unchecked")
	default DoubleArraySymbol<S> asDoubleArray() {
		return DoubleArraySymbol.symbol((Symbol<S, Double[]>) this);
	}

	@SuppressWarnings("unchecked")
	default BooleanPrimitiveArraySymbol<S> asBooleanPrimitiveArray() {
		return BooleanPrimitiveArraySymbol.symbol((Symbol<S, boolean[]>) this);
	}

	@SuppressWarnings("unchecked")
	default DoublePrimitiveArraySymbol<S> asDoublePrimitiveArray() {
		return DoublePrimitiveArraySymbol.symbol((Symbol<S, double[]>) this);
	}

	@SuppressWarnings("unchecked")
	default DoublePrimitiveArray2Symbol<S> asDoublePrimitiveArray2() {
		return DoublePrimitiveArray2Symbol.symbol((Symbol<S, double[][]>) this);
	}

	@Override
	default <W> Symbol<S, W> andThen(Function<? super V, ? extends W> after) {
		return Function.super.andThen(after)::apply;
	}

	default <W, R> Symbol<S, R> andThen(BiFunction<V, W, R> after, W y) {
		return store -> after.apply(this.apply(store), y);
	}

	default <W, R> Symbol<S, R> andThen(BiFunction<V, W, R> after, Function<S, W> y) {
		return store -> after.apply(this.apply(store), y.apply(store));
	}

	default BooleanSymbol<S> isEquals(Function<S, V> y) {
		return andThen(BiFunctionUtils.notNullEval(ObjectUtils::areEquals), y::apply).asBoolean();
	}

	default BooleanSymbol<S> isNotEquals(Function<S, V> y) {
		return andThen(BiFunctionUtils.notNullEval(ObjectUtils::areNotEquals), y::apply).asBoolean();
	}

}
