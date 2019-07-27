package org.utilities.symbolicmath.utils.array;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

import org.utilities.symbolicmath.utils.function.ArrayFactory;

public class ArrayUtils {

	public static <V, R> R[] apply(V[] v, Function<V, R> func, ArrayFactory<R> factory) {
		R[] r = factory.apply(v.length);
		for (int i = 0; i < v.length; i++) {
			r[i] = func.apply(v[i]);
		}
		return r;
	}

	public static <V, W, R> R[] apply(V[] v, BiFunction<V, W, R> func, W[] w, ArrayFactory<R> factory) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		R[] r = factory.apply(v.length);
		for (int i = 0; i < v.length; i++) {
			r[i] = func.apply(v[i], w[i]);
		}
		return r;
	}

	public static <V> double[] apply(V[] v, ToDoubleFunction<V> func) {
		double[] d = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			d[i] = func.applyAsDouble(v[i]);
		}
		return d;
	}

	public static <V, W> double[] apply(V[] v, ToDoubleBiFunction<V, W> func, W[] w) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		double[] d = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			d[i] = func.applyAsDouble(v[i], w[i]);
		}
		return d;
	}

	public static <V> boolean[] apply(V[] v, Predicate<V> func) {
		boolean[] b = new boolean[v.length];
		for (int i = 0; i < v.length; i++) {
			b[i] = func.test(v[i]);
		}
		return b;
	}

	public static <V, W> boolean[] apply(V[] v, BiPredicate<V, W> func, W[] w) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		boolean[] b = new boolean[v.length];
		for (int i = 0; i < v.length; i++) {
			b[i] = func.test(v[i], w[i]);
		}
		return b;
	}

	public static <V> V[] filter(V[] values, boolean[] filter, ArrayFactory<V> factory) {
		int length = 0;
		for (int i = 0; i < values.length; i++) {
			if (filter[i]) {
				length++;
			}
		}
		V[] result = factory.apply(length);
		for (int i = 0; i < values.length; i++) {
			if (filter[i]) {
				result[i] = values[i];
			}
		}
		return result;
	}

}
