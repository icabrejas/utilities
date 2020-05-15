package org.utilities.symbolicmath.utils;

public class LogicalUtils {

	public static boolean not(boolean x) {
		return !x;
	}

	public static boolean lower(double x, double y) {
		return x < y;
	}

	public static boolean lowerOrEquals(double x, double y) {
		return x <= y;
	}

	public static boolean greater(double x, double y) {
		return x > y;
	}

	public static boolean greaterOrEquals(double x, double y) {
		return x >= y;
	}

	public static boolean equals(double x, double y) {
		return x == y;
	}

	public static boolean notEquals(double x, double y) {
		return x != y;
	}

	public static <Y> Y filter(boolean x, Y y) {
		return x ? y : null;
	}

	public static double parseDouble(boolean x) {
		return x ? 1 : 0;
	}

	public static boolean parseBoolean(double x) {
		if (x == 1) {
			return true;
		} else if (x == 0) {
			return false;
		} else {
			throw new Error(); // FIXME
		}
	}

	public static boolean all(boolean[] x) {
		boolean all = true;
		for (int i = 0; i < x.length && all; i++) {
			all = x[i];
		}
		return all;
	}

	public static boolean any(boolean[] x) {
		boolean any = false;
		for (int i = 0; i < x.length && !any; i++) {
			any = x[i];
		}
		return any;
	}

	public static Boolean all(Boolean[] x) {
		Boolean all = true;
		for (int i = 0; i < x.length && (all == null || all); i++) {
			if (x[i] == null || !x[i]) {
				all = x[i];
			}
		}
		return all;
	}

	public static Boolean any(Boolean[] x) {
		Boolean any = false;
		for (int i = 0; i < x.length && (any == null || !any); i++) {
			if (x[i] == null || x[i]) {
				any = x[i];
			}
		}
		return any;
	}

}
