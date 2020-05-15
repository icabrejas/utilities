package org.utilities.core;

import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.function.Supplier;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.util.function.FunctionPlus;

public class UtilitiesFunction {

	public static <T, R> Function<T, R> getter(Class<T> type, String name)
			throws NoSuchFieldException, SecurityException {
		Field field = type.getDeclaredField(name);
		return getter(field);
	}

	@SuppressWarnings("unchecked")
	public static <T, R> Function<T, R> getter(Field field) {
		return t -> {
			try {
				return (R) field.get(t);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new QuietException(e);
			}
		};
	}

	public static <T, R> Supplier<R> parseSupplier(Function<T, R> function, T t) {
		return () -> function.apply(t);
	}

	public static <T, R> FunctionPlus<T, R> applyIfNotNull(Function<T, R> function) {
		return t -> applyIfNotNull(t, function);
	}

	public static <T, R> R applyIfNotNull(T t, Function<T, R> function) {
		return t != null ? function.apply(t) : null;
	}

	public static <T, U, R> FunctionPlus<T, R> compose(Function<U, R> after, Function<T, U> before) {
		return after.compose(before)::apply;
	}

	public static <T, R> Function<T, R> parseQuiet(Noisy<T, R> noisy) {
		return t -> {
			try {
				return noisy.apply(t);
			} catch (Exception e) {
				throw new QuietException(e);
			}
		};
	}

	public static interface Noisy<T, R> {

		R apply(T t) throws Exception;

		public static <T, R> R tryToApply(UtilitiesFunction.Noisy<T, R> function, T t, int maxTimes) {
			int fails = 0;
			R result = null;
			while (result == null) {
				try {
					result = function.apply(t);
				} catch (Exception e) {
					if (++fails == maxTimes) {
						throw new QuietException(e);
					}
				}
			}
			return result;
		}

	}

}
