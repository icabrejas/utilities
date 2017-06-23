package org.utilities.core.lang;

import java.util.Locale;
import java.util.function.BiFunction;

public class UtilitiesNumber {

	public static Double parseDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static Double min(Double a, Double b) {
		return applyIfNotNull(a, b, Math::min);
	}

	public static Double max(Double a, Double b) {
		return applyIfNotNull(a, b, Math::max);
	}

	public static Double sum(Double a, Double b) {
		return applyIfNotNull(a, b, Double::sum);
	}

	public static Double mult(Double a, Double b) {
		return applyIfNotNull(a, b, (x, y) -> x * y);
	}

	public static double notNull(Double x) {
		return x != null ? x : Double.NaN;
	}

	public static boolean isNaN(Double x) {
		return x != null && Double.isNaN(x);
	}

	public static Integer min(Integer a, Integer b) {
		return applyIfNotNull(a, b, Math::min);
	}

	public static Integer max(Integer a, Integer b) {
		return applyIfNotNull(a, b, Math::max);
	}

	public static Integer sum(Integer a, Integer b) {
		return applyIfNotNull(a, b, Integer::sum);
	}

	public static Integer mult(Integer a, Integer b) {
		return applyIfNotNull(a, b, (x, y) -> x * y);
	}

	public static Long min(Long a, Long b) {
		return applyIfNotNull(a, b, Math::min);
	}

	public static Long max(Long a, Long b) {
		return applyIfNotNull(a, b, Math::max);
	}

	public static Long sum(Long a, Long b) {
		return applyIfNotNull(a, b, Long::sum);
	}

	public static Long mult(Long a, Long b) {
		return applyIfNotNull(a, b, (x, y) -> x * y);
	}

	private static <T> T applyIfNotNull(T a, T b, BiFunction<T, T, T> f) {
		if (a != null) {
			return b != null ? f.apply(a, b) : a;
		} else {
			return b;
		}
	}

	public static Double toSignificantFigures(Double x, int significantFigures) {
		String rounded = format(x, significantFigures);
		return Double.parseDouble(rounded);
	}

	public static String format(Double x, int significantFigures) {
		return String.format(Locale.ENGLISH, "%." + significantFigures + "G", x);
	}

}
