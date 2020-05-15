package org.utilities.core;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

import org.utilities.core.util.function.ArrayFactory;
import org.utilities.core.util.function.IntBiFunctionPlus;
import org.utilities.core.util.function.IntBiPredicatePlus;
import org.utilities.core.util.function.IntBinaryOperatorPlus;
import org.utilities.core.util.function.IntFunctionPlus;
import org.utilities.core.util.function.IntPredicatePlus;
import org.utilities.core.util.function.IntToDoubleBiFunctionPlus;
import org.utilities.core.util.function.IntToDoubleFunctionPlus;
import org.utilities.core.util.function.IntToLongBiFunctionPlus;
import org.utilities.core.util.function.IntToLongFunctionPlus;
import org.utilities.core.util.function.IntUnaryOperatorPlus;

public class UtilitiesArray {

	public static IntPredicatePlus getter(boolean[] src) {
		return i -> src[i];
	}

	public static IntUnaryOperatorPlus getter(int[] src) {
		return i -> src[i];
	}

	public static IntToLongFunctionPlus getter(long[] src) {
		return i -> src[i];
	}

	public static IntToDoubleFunctionPlus getter(double[] src) {
		return i -> src[i];
	}

	public static <R> IntFunctionPlus<R> getter(R[] src) {
		return i -> src[i];
	}

	public static IntBiPredicatePlus getter(boolean[][] src) {
		return (int i, int j) -> src[i][j];
	}

	public static IntBinaryOperatorPlus getter(int[][] src) {
		return (int i, int j) -> src[i][j];
	}

	public static IntToLongBiFunctionPlus getter(long[][] src) {
		return (int i, int j) -> src[i][j];
	}

	public static IntToDoubleBiFunctionPlus getter(double[][] src) {
		return (int i, int j) -> src[i][j];
	}

	public static <R> IntBiFunctionPlus<R> getter(R[][] src) {
		return (int i, int j) -> src[i][j];
	}

	public static boolean[] create(IntPredicate generator, int n) {
		boolean[] array = new boolean[n];
		for (int i = 0; i < array.length; i++) {
			array[i] = generator.test(i);
		}
		return array;
	}

	public static int[] create(IntUnaryOperator generator, int n) {
		int[] array = new int[n];
		Arrays.setAll(array, generator);
		return array;
	}

	public static long[] create(IntToLongFunction generator, int n) {
		long[] array = new long[n];
		Arrays.setAll(array, generator);
		return array;
	}

	public static double[] create(IntToDoubleFunction generator, int n) {
		double[] array = new double[n];
		Arrays.setAll(array, generator);
		return array;
	}

	public static <R> R[] create(IntFunction<R> generator, int n, ArrayFactory<R> factory) {
		R[] array = factory.create(n);
		Arrays.setAll(array, generator);
		return array;
	}

	// ----------------------- boolean -----------------------

	public static boolean[] mapAll(boolean[] v, Predicate<Boolean> mapper) {
		return create(getter(v).andThen(mapper::test)::apply, v.length);
	}

	public static int[] mapAll(boolean[] v, ToIntFunction<Boolean> mapper) {
		return create(getter(v).andThen(mapper::applyAsInt)::apply, v.length);
	}

	public static long[] mapAll(boolean[] v, ToLongFunction<Boolean> mapper) {
		return create(getter(v).andThen(mapper::applyAsLong)::apply, v.length);
	}

	public static double[] mapAll(boolean[] v, ToDoubleFunction<Boolean> mapper) {
		return create(getter(v).andThen(mapper::applyAsDouble)::apply, v.length);
	}

	public static <R> R[] mapAll(boolean[] v, Function<Boolean, R> mapper, ArrayFactory<R> factory) {
		return create(getter(v).andThen(mapper), v.length, factory);
	}

	// ----------------------- int -----------------------

	public static boolean[] mapAll(int[] v, IntPredicate mapper) {
		return create(getter(v).andThen(mapper::test)::apply, v.length);
	}

	public static int[] mapAll(int[] v, IntUnaryOperator mapper) {
		return create(getter(v).andThen(mapper), v.length);
	}

	public static long[] mapAll(int[] v, IntToLongFunction mapper) {
		return create(getter(v).andThen(mapper::applyAsLong)::apply, v.length);
	}

	public static double[] mapAll(int[] v, IntToDoubleFunction mapper) {
		return create(getter(v).andThen(mapper::applyAsDouble)::apply, v.length);
	}

	public static <R> R[] mapAll(int[] v, IntFunction<R> mapper, ArrayFactory<R> factory) {
		return create(getter(v).andThen(mapper::apply), v.length, factory);
	}

	// ----------------------- long -----------------------

	public static boolean[] mapAll(long[] v, LongPredicate mapper) {
		return create(getter(v).andThen(mapper::test)::apply, v.length);
	}

	public static int[] mapAll(long[] v, LongToIntFunction mapper) {
		return create(getter(v).andThen((Function<Long, Integer>) mapper::applyAsInt)::apply, v.length);
	}

	public static long[] mapAll(long[] v, LongUnaryOperator mapper) {
		return create(getter(v).andThen(mapper), v.length);
	}

	public static double[] mapAll(long[] src, LongToDoubleFunction mapper) {
		return create(getter(src).andThen(mapper::applyAsDouble)::apply, src.length);
	}

	public static <R> R[] mapAll(long[] v, LongFunction<R> mapper, ArrayFactory<R> factory) {
		return create(getter(v).andThen(mapper::apply), v.length, factory);
	}

	// ----------------------- double -----------------------

	public static boolean[] mapAll(double[] v, DoublePredicate mapper) {
		return create(getter(v).andThen(mapper::test)::apply, v.length);
	}

	public static int[] mapAll(double[] v, DoubleToIntFunction mapper) {
		return create(getter(v).andThen((Function<Double, Integer>) mapper::applyAsInt)::apply, v.length);
	}

	public static long[] mapAll(double[] v, DoubleToLongFunction mapper) {
		return create(getter(v).andThen((Function<Double, Long>) mapper::applyAsLong)::apply, v.length);
	}

	public static double[] mapAll(double[] v, DoubleUnaryOperator mapper) {
		return create(getter(v).andThen(mapper), v.length);
	}

	public static <R> R[] mapAll(double[] v, DoubleFunction<R> mapper, ArrayFactory<R> factory) {
		return create(getter(v).andThen(mapper::apply), v.length, factory);
	}

	// ----------------------- class -----------------------

	public static <V> boolean[] mapAll(V[] v, Predicate<V> mapper) {
		return create(getter(v).andThen(mapper::test)::apply, v.length);
	}

	public static <V> int[] mapAll(V[] v, ToIntFunction<V> mapper) {
		return create(getter(v).andThen(mapper::applyAsInt)::apply, v.length);
	}

	public static <V> long[] mapAll(V[] v, ToLongFunction<V> mapper) {
		return create(getter(v).andThen(mapper::applyAsLong)::apply, v.length);
	}

	public static <V> double[] mapAll(V[] v, ToDoubleFunction<V> mapper) {
		return create(getter(v).andThen(mapper::applyAsDouble)::apply, v.length);
	}

	public static <V, R> R[] mapAll(V[] v, Function<V, R> mapper, ArrayFactory<R> factory) {
		return create(getter(v).andThen(mapper), v.length, factory);
	}

	// -----------------------------------------------------
	// ----------------------- Pairs -----------------------
	// -----------------------------------------------------

	// ----------------------- boolean -----------------------

	public static boolean[] mapAll(boolean[] v, boolean[] w, BiPredicate<Boolean, Boolean> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static int[] mapAll(boolean[] v, boolean[] w, ToIntBiFunction<Boolean, Boolean> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static long[] mapAll(boolean[] v, boolean[] w, ToLongBiFunction<Boolean, Boolean> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static double[] mapAll(boolean[] v, boolean[] w, ToDoubleBiFunction<Boolean, Boolean> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static <R> R[] mapAll(boolean[] v, boolean[] w, BiFunction<Boolean, Boolean, R> func,
			ArrayFactory<R> factory) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length, factory);
	}

	// ----------------------- int -----------------------
	// ----------------------- long -----------------------
	// ----------------------- double -----------------------

	public static boolean[] mapAll(double[] v, double[] w, BiPredicate<Double, Double> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static int[] mapAll(double[] v, double[] w, ToIntBiFunction<Double, Double> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static long[] mapAll(double[] v, double[] w, ToLongBiFunction<Double, Double> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static double[] mapAll(double[] v, double[] w, DoubleBinaryOperator func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static <R> R[] mapAll(double[] v, double[] w, BiFunction<Double, Double, R> func, ArrayFactory<R> factory) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length, factory);
	}

	// ----------------------- class -----------------------

	public static <V, W> boolean[] mapAll(V[] v, W[] w, BiPredicate<V, W> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static <V, W> int[] mapAll(V[] v, W[] w, ToIntBiFunction<V, W> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static <V, W> long[] mapAll(V[] v, W[] w, ToLongBiFunction<V, W> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static <V, W> double[] mapAll(V[] v, W[] w, ToDoubleBiFunction<V, W> func) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), func), v.length);
	}

	public static <V, W, R> R[] mapAll(V[] v, W[] w, BiFunction<V, W, R> mapper, ArrayFactory<R> factory) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		return create(UtilitiesBiFunction.compose(getter(v), getter(w), mapper), v.length, factory);
	}

	// -------------------------------------------------------------

	public static <V> V[] filter(V[] values, boolean[] filter, ArrayFactory<V> factory) {
		int length = 0;
		for (int i = 0; i < values.length; i++) {
			if (filter[i]) {
				length++;
			}
		}
		V[] result = factory.create(length);
		int j = 0;
		for (int i = 0; i < values.length; i++) {
			if (filter[i]) {
				result[j++] = values[i];
			}
		}
		return result;
	}

}
