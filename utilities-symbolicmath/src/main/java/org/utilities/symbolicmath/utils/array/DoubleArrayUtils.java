package org.utilities.symbolicmath.utils.array;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import org.utilities.symbolicmath.utils.function.ArrayFactory;

public class DoubleArrayUtils {

	public static <R> R[] apply(double[] v, DoubleFunction<R> func, ArrayFactory<R> factory) {
		R[] r = factory.apply(v.length);
		for (int i = 0; i < v.length; i++) {
			r[i] = func.apply(v[i]);
		}
		return r;
	}

	public static <R> R[] apply(double[] v, BiFunction<Double, Double, R> func, double[] w, ArrayFactory<R> factory) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		R[] r = factory.apply(v.length);
		for (int i = 0; i < v.length; i++) {
			r[i] = func.apply(v[i], w[i]);
		}
		return r;
	}

	public static double[] apply(double[] v, DoubleUnaryOperator func) {
		double[] d = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			d[i] = func.applyAsDouble(v[i]);
		}
		return d;
	}

	public static double[] apply(double[] v, DoubleBinaryOperator func, double[] w) {
		if (v.length != w.length) {
			throw new Error(); // FIXME specific error
		}
		double[] d = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			d[i] = func.applyAsDouble(v[i], w[i]);
		}
		return d;
	}

	public static boolean[] apply(double[] v, DoublePredicate func) {
		boolean[] b = new boolean[v.length];
		for (int i = 0; i < v.length; i++) {
			b[i] = func.test(v[i]);
		}
		return b;
	}

	public static boolean[] apply(double[] v, BiPredicate<Double, Double> func, double[] w) {
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
