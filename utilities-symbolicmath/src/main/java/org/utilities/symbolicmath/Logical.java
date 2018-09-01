package org.utilities.symbolicmath;

public class Logical {

	public static boolean not(boolean x) {
		return !x;
	}

	public static boolean extrictLower(Double x, Double y) {
		return x < y;
	}

	public static boolean lower(Double x, Double y) {
		return x <= y;
	}

	public static boolean extrictGreater(Double x, Double y) {
		return x > y;
	}

	public static boolean greater(Double x, Double y) {
		return x >= y;
	}

	public static boolean distinct(Double x, Double y) {
		return !x.equals(y);
	}
}
