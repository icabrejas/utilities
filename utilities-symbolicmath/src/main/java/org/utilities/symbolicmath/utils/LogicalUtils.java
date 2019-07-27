package org.utilities.symbolicmath.utils;

public class LogicalUtils {

	public static boolean not(boolean x) {
		return !x;
	}

	public static boolean extrictLower(double x, double y) {
		return x < y;
	}

	public static boolean lower(double x, double y) {
		return x <= y;
	}

	public static boolean extrictGreater(double x, double y) {
		return x > y;
	}

	public static boolean greater(double x, double y) {
		return x >= y;
	}

	public static boolean coequals(double x, double y) {
		return x == y;
	}

	public static boolean notCoequals(double x, double y) {
		return x != y;
	}

	public static <Y> Y filter(boolean x, Y y) {
		return x ? y : null;
	}

	public static double parseDouble(boolean x) {
		return x ? 1.0 : 0.0;
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

}
