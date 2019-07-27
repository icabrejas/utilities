package org.utilities.symbolicmath.utils.array;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

import org.utilities.symbolicmath.utils.function.ArrayFactory;

public class BooleanArrayUtils {

	public static <R> R[] apply(boolean[] v, Function<Boolean, R> func, ArrayFactory<R> factory) {
		R[] r = factory.apply(v.length);
		for (int i = 0; i < v.length; i++) {
			r[i] = func.apply(v[i]);
		}
		return r;
	}

	public static <R> R[] apply(boolean[] v, BiFunction<Boolean, Boolean, R> func, boolean[] w,
			ArrayFactory<R> factory) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		R[] r = factory.apply(v.length);
		for (int i = 0; i < v.length; i++) {
			r[i] = func.apply(v[i], w[i]);
		}
		return r;
	}

	public static double[] apply(boolean[] v, ToDoubleFunction<Boolean> func) {
		double[] d = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			d[i] = func.applyAsDouble(v[i]);
		}
		return d;
	}

	public static double[] apply(boolean[] v, ToDoubleBiFunction<Boolean, Boolean> func, boolean[] w) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		double[] d = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			d[i] = func.applyAsDouble(v[i], w[i]);
		}
		return d;
	}

	public static boolean[] apply(boolean[] v, Predicate<Boolean> func) {
		boolean[] b = new boolean[v.length];
		for (int i = 0; i < v.length; i++) {
			b[i] = func.test(v[i]);
		}
		return b;
	}

	public static boolean[] apply(boolean[] v, BiPredicate<Boolean, Boolean> func, boolean[] w) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		boolean[] b = new boolean[v.length];
		for (int i = 0; i < v.length; i++) {
			b[i] = func.test(v[i], w[i]);
		}
		return b;
	}

}
