package org.utilities.core.util.function;

import java.util.Iterator;
import java.util.function.BiFunction;

public interface Reducer<T> extends BiFunctionPlus<T, T, T> {

	default T reduce(Iterable<T> it) {
		return reduce(it.iterator());
	}

	default T reduce(Iterator<T> it) {
		T reduced = null;
		while (it.hasNext()) {
			reduced = reduce(reduced, it.next());
		}
		return reduced;
	}

	default T reduce(T a, T b) {
		if (b == null) {
			return a;
		} else if (a != null) {
			return apply(a, b);
		} else {
			return b;
		}
	}

	public static <T> Reducer<T> newInstance(BiFunction<T, T, T> reducer) {
		return reducer::apply;
	}

	public static <T> T reduce(Iterable<T> it, BiFunction<T, T, T> reducer) {
		return newInstance(reducer).reduce(it);
	}

}
