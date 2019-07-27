package org.utilities.core.lang.iterable;

import java.util.function.Function;
import java.util.function.Supplier;

import org.utilities.core.util.function.FunctionPlus;

public interface Entry<T> {

	T getContent();

	public static <T> Entry<T> from(Supplier<T> content) {
		return content::get;
	}

	public static <T> Entry<T> newInstance(T content) {
		return from(() -> content);
	}

	public static <T1, T2> Function<Entry<T1>, Entry<T2>> entryMapper(Function<T1, T2> content) {
		return entryMapper(FunctionPlus.newInstance(content));
	}

	public static <T, I, R> Function<T, Entry<R>> mapper(Function<T, R> content) {
		return mapper(FunctionPlus.newInstance(content));
	}

}
