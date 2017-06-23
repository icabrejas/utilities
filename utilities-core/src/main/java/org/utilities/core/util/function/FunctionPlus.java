package org.utilities.core.util.function;

import java.lang.reflect.Field;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;

public interface FunctionPlus<T, R> extends Function<T, R> {

	@Override
	default <V> FunctionPlus<T, V> andThen(Function<? super R, ? extends V> after) {
		return newInstance(Function.super.andThen(after));
	}

	default <U, V> FunctionPlus<T, V> andThen(BiFunction<R, U, V> after, U u) {
		return andThen(BiFunctionPlus.parseFunction(after, u));
	}

	@Override
	default <V> FunctionPlus<V, R> compose(Function<? super V, ? extends T> before) {
		return newInstance(Function.super.compose(before));
	}

	public static <T, R, U> FunctionPlus<T, U> compose(Function<R, U> g, Function<T, R> f) {
		return newInstance(g.compose(f));
	}

	public static <T, R> FunctionPlus<T, R> newInstance(Function<T, R> function) {
		return function::apply;
	}

	public static <T, R> SupplierPlus<R> parseSupplier(Function<T, R> function, T t) {
		return () -> function.apply(t);
	}

	default SupplierPlus<R> parseSupplier(T t) {
		return parseSupplier(this, t);
	}

	public static <T, R> FunctionPlus<T, R> getter(Class<T> type, String name)
			throws NoSuchFieldException, SecurityException {
		Field field = type.getDeclaredField(name);
		return getter(field);
	}

	@SuppressWarnings("unchecked")
	public static <T, R> FunctionPlus<T, R> getter(Field field) {
		return t -> {
			try {
				return (R) field.get(t);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new QuietException(e);
			}
		};
	}

	public static <T, R> FunctionPlus<T, R> applyIfNotNull(Function<T, R> function) {
		return t -> applyIfNotNull(t, function);
	}

	public static <T, R> R applyIfNotNull(T t, Function<T, R> function) {
		return t != null ? function.apply(t) : null;
	}

	public static <T, R> FunctionPlus<T, R> parseQuiet(Noisy<T, R> noisy) {
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

		public static <T, R> R tryToApply(FunctionPlus.Noisy<T, R> function, T t, int maxTimes) {
			int fails = 0;
			R result = null;
			while (result == null) {
				try {
					result = function.apply(t);
				} catch (Exception e) {
					if (++fails == 3) {
						throw new QuietException(e);
					}
				}
			}
			return result;
		}

	}

}
