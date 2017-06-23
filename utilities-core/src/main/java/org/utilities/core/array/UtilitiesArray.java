package org.utilities.core.array;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;

import org.utilities.core.util.function.IntBiFunctionPlus;
import org.utilities.core.util.function.IntBinaryOperatorPlus;
import org.utilities.core.util.function.IntFunctionPlus;
import org.utilities.core.util.function.IntToDoubleBiFunctionPlus;
import org.utilities.core.util.function.IntToDoubleFunctionPlus;
import org.utilities.core.util.function.IntToLongBiFunctionPlus;
import org.utilities.core.util.function.IntToLongFunctionPlus;
import org.utilities.core.util.function.IntUnaryOperatorPlus;

public class UtilitiesArray {

	public static double[] create(IntToDoubleFunction generator, int n) {
		double[] array = new double[n];
		Arrays.setAll(array, generator);
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

	public static IntToDoubleFunctionPlus getter(double[] src) {
		return (int i) -> src[i];
	}

	public static IntUnaryOperatorPlus getter(int[] src) {
		return (int i) -> src[i];
	}

	public static IntToLongFunctionPlus getter(long[] src) {
		return (int i) -> src[i];
	}

	public static <R> IntFunctionPlus<R> getter(R[] src) {
		return (int i) -> src[i];
	}
	
	
	public static IntToDoubleBiFunctionPlus getter(double[][] src) {
		return (int i, int j) -> src[i][j];
	}

	public static IntBinaryOperatorPlus getter(int[][] src) {
		return (int i, int j) -> src[i][j];
	}

	public static IntToLongBiFunctionPlus getter(long[][] src) {
		return (int i, int j) -> src[i][j];
	}

	public static <R> IntBiFunctionPlus<R> getter(R[][] src) {
		return (int i, int j) -> src[i][j];
	}

	public static double[] map(DoubleUnaryOperator mapper, double[] src) {
		return create(getter(src).andThen(mapper), src.length);
	}

	public static int[] map(IntUnaryOperator mapper, int[] src) {
		return create(getter(src).andThen(mapper), src.length);
	}

	public static long[] map(LongUnaryOperator mapper, long[] src) {
		return create(getter(src).andThen(mapper), src.length);
	}

	public static <T, R> void map(Function<T, R> mapper, T[] src, R[] dest) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = mapper.apply(src[i]);
		}
	}

}
