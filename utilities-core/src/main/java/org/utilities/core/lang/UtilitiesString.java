package org.utilities.core.lang;

import java.util.List;

import org.utilities.core.lang.iterable.IterablePipe;

public class UtilitiesString {

	public static int compareCharAt(String a, String b, int i) {
		return Character.compare(a.charAt(i), b.charAt(i));
	}

	public static List<String> paste(String a, Iterable<String> b, String sep) {
		return IterablePipe.newInstance(b)
				.map(b_ -> a + sep + b_)
				.toList();
	}

	public static List<String> paste(Iterable<String> a, String b, String sep) {
		return IterablePipe.newInstance(a)
				.map(a_ -> a_ + sep + b)
				.toList();
	}

	public static List<String> paste0(String a, Iterable<String> b) {
		return paste(a, b, "");
	}

	public static List<String> paste0(Iterable<String> a, String b) {
		return paste(a, b, "");
	}

	public static String paste(Iterable<String> parts, String sep) {
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
