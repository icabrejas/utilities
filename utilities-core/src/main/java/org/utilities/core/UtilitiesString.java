package org.utilities.core;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UtilitiesString {

	public static int compareCharAt(String a, String b, int i) {
		return Character.compare(a.charAt(i), b.charAt(i));
	}

	public static List<String> paste(Object a, Collection<?> b, String sep) {
		return b.stream()
				.map(b_ -> a + sep + b_)
				.collect(Collectors.toList());
	}

	public static List<String> paste(Collection<?> a, Object b, String sep) {
		return a.stream()
				.map(a_ -> a_ + sep + b)
				.collect(Collectors.toList());
	}

	public static List<String> paste0(Object a, Collection<?> b) {
		return paste(a, b, "");
	}

	public static List<String> paste0(Collection<?> a, Object b) {
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
