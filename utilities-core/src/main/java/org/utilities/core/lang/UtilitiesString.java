package org.utilities.core.lang;

import java.util.List;

import org.utilities.core.lang.iterable.IterablePipe;

public class UtilitiesString {

	public static int compareCharAt(String a, String b, int i) {
		return Character.compare(a.charAt(i), b.charAt(i));
	}

	public static List<String> paste(Object a, Iterable<?> b, String sep) {
		return IterablePipe.from(b)
				.map(b_ -> a + sep + b_)
				.toList();
	}

	public static List<String> paste(Iterable<?> a, Object b, String sep) {
		return IterablePipe.from(a)
				.map(a_ -> a_ + sep + b)
				.toList();
	}

	public static List<String> paste0(Object a, Iterable<?> b) {
		return paste(a, b, "");
	}

	public static List<String> paste0(Iterable<?> a, Object b) {
		return paste(a, b, "");
	}

	public static String paste(Iterable<?> parts, String sep) {
		StringBuilder builder = new StringBuilder();
		for (Object part : parts) {
			builder.append(part + sep);
		}
		if (builder.length() > 0 & 0 < sep.length()) {
			builder.delete(builder.length() - sep.length(), builder.length());
		}
		return builder.toString();
	}

	public static String paste0(Iterable<String> parts) {
		return paste(parts, "");
	}

}
