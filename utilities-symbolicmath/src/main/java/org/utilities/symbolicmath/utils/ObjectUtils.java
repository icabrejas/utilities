package org.utilities.symbolicmath.utils;

public class ObjectUtils {

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	public static boolean areEquals(Object x, Object y) {
		return x.equals(y);
	}

	public static boolean areNotEquals(Object x, Object y) {
		return !x.equals(y);
	}

}
