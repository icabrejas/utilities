package org.utilities.core.lang.iterable.map;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.lang.iterable.track.IPTracker;

public interface IPMapper<T, R> extends IPTracker<T> {

	R map(int serialNumber, T t);

	public static <T, R> IPMapperImpl<T, R> concurrent(BiFunction<Integer, T, R> map) {
		return new IPMapperImpl<>(IPTracker.concurrent(), map);
	}

	public static <T, R> IPMapperImpl<T, R> concurrent(Function<T, R> map) {
		return new IPMapperImpl<>(IPTracker.concurrent(), map);
	}

}
