package org.utilities.core;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToLongBiFunction;

import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.function.FunctionPlus;
import org.utilities.core.util.pair.Pair;

public class UtilitiesBiFunction {

	public static <T, U, R> BiFunctionPlus<T, U, R> dummy() {
		return dummy(null);
	}

	public static <T, U, R> BiFunctionPlus<T, U, R> dummy(R r) {
		return (T t, U u) -> r;
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

	public static <T, U, R> BiFunctionPlus<U, T, R> flip(BiFunction<T, U, R> biFunction) {
		return (u, t) -> biFunction.apply(t, u);
	}

	public static <T, U, R> BiFunctionPlus<T, U, R> applyIfNotNull(BiFunction<T, U, R> biFunction) {
		return (t, u) -> applyIfNotNull(t, u, biFunction);
	}

	public static <T, U, R> R applyIfNotNull(T t, U u, BiFunction<T, U, R> biFunction) {
		return (t != null & u != null) ? biFunction.apply(t, u) : null;
	}

	// ----------------- simple compose int-boolean ----------------

	public static IntPredicate compose(IntPredicate left, IntPredicate right, BiPredicate<Boolean, Boolean> after) {
		return i -> after.test(left.test(i), right.test(i));
	}

	public static IntUnaryOperator compose(IntPredicate left, IntPredicate right,
			ToIntBiFunction<Boolean, Boolean> after) {
		return i -> after.applyAsInt(left.test(i), right.test(i));
	}

	public static IntToLongFunction compose(IntPredicate left, IntPredicate right,
			ToLongBiFunction<Boolean, Boolean> after) {
		return i -> after.applyAsLong(left.test(i), right.test(i));
	}

	public static IntToDoubleFunction compose(IntPredicate left, IntPredicate right,
			ToDoubleBiFunction<Boolean, Boolean> after) {
		return i -> after.applyAsDouble(left.test(i), right.test(i));
	}

	public static <R> IntFunction<R> compose(IntPredicate left, IntPredicate right,
			BiFunction<Boolean, Boolean, R> after) {
		return i -> after.apply(left.test(i), right.test(i));
	}

	// ----------------- simple compose int-double ----------------

	public static IntPredicate compose(IntToDoubleFunction left, IntToDoubleFunction right,
			BiPredicate<Double, Double> after) {
		return i -> after.test(left.applyAsDouble(i), right.applyAsDouble(i));
	}

	public static IntUnaryOperator compose(IntToDoubleFunction left, IntToDoubleFunction right,
			ToIntBiFunction<Double, Double> after) {
		return i -> after.applyAsInt(left.applyAsDouble(i), right.applyAsDouble(i));
	}

	public static IntToLongFunction compose(IntToDoubleFunction left, IntToDoubleFunction right,
			ToLongBiFunction<Double, Double> after) {
		return i -> after.applyAsLong(left.applyAsDouble(i), right.applyAsDouble(i));
	}

	public static IntToDoubleFunction compose(IntToDoubleFunction left, IntToDoubleFunction right,
			DoubleBinaryOperator after) {
		return i -> after.applyAsDouble(left.applyAsDouble(i), right.applyAsDouble(i));
	}

	public static <R> IntFunction<R> compose(IntToDoubleFunction left, IntToDoubleFunction right,
			BiFunction<Double, Double, R> after) {
		return i -> after.apply(left.applyAsDouble(i), right.applyAsDouble(i));
	}
	// ----------------- simple compose int-class ----------------

	public static <T, U> IntPredicate compose(IntFunction<? extends T> left, IntFunction<? extends U> right,
			BiPredicate<T, U> after) {
		return i -> after.test(left.apply(i), right.apply(i));
	}

	public static <T, U> IntUnaryOperator compose(IntFunction<? extends T> left, IntFunction<? extends U> right,
			ToIntBiFunction<T, U> after) {
		return i -> after.applyAsInt(left.apply(i), right.apply(i));
	}

	public static <T, U> IntToLongFunction compose(IntFunction<? extends T> left, IntFunction<? extends U> right,
			ToLongBiFunction<T, U> after) {
		return i -> after.applyAsLong(left.apply(i), right.apply(i));
	}

	public static <T, U> IntToDoubleFunction compose(IntFunction<? extends T> left, IntFunction<? extends U> right,
			ToDoubleBiFunction<T, U> after) {
		return i -> after.applyAsDouble(left.apply(i), right.apply(i));
	}

	public static <T, U, R> IntFunction<R> compose(IntFunction<? extends T> left, IntFunction<? extends U> right,
			BiFunction<T, U, R> after) {
		return i -> after.apply(left.apply(i), right.apply(i));
	}

	// ----------------- compose class ----------------

	public static <V, W, T, U, R> BiFunction<V, W, R> compose(Function<? super V, ? extends T> left,
			Function<? super W, ? extends U> right, BiFunction<T, U, R> after) {
		return (v, w) -> after.apply(left.apply(v), right.apply(w));
	}

}
