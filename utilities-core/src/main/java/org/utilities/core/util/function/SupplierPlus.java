package org.utilities.core.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.util.concurrent.UtilitiesThread;

public interface SupplierPlus<T> extends Supplier<T> {

	public static <T> SupplierPlus<T> newInstance(Supplier<T> supplier) {
		return supplier::get;
	}

	public static <T> SupplierPlus<T> newInstance(T t) {
		return () -> t;
	}

	public static <T, U, R> SupplierPlus<R> map(Supplier<T> supplier, BiFunction<T, U, R> mapper, U u) {
		return () -> mapper.apply(supplier.get(), u);
	}

	public static <T, R> SupplierPlus<R> map(Supplier<T> supplier, Function<T, R> mapper) {
		return () -> mapper.apply(supplier.get());
	}

	public default <R> SupplierPlus<R> map(Function<T, R> mapper) {
		return SupplierPlus.map(this, mapper);
	}

	public default <U, R> SupplierPlus<R> map(BiFunction<T, U, R> mapper, U u) {
		return SupplierPlus.map(this, mapper, u);
	}

	public static <T> SupplierPlus<T> parseQuiet(Noisy<T> noisy) {
		return () -> {
			try {
				return noisy.get();
			} catch (Exception e) {
				throw new QuietException(e);
			}
		};
	}
	
	public static interface Noisy<T> {

		T get() throws Exception;

		public static <T> T tryToGet(SupplierPlus.Noisy<T> supplier, int maxTimes) {
			int fails = 0;
			T result = null;
			while (result == null) {
				try {
					result = supplier.get();
				} catch (Exception e) {
					if (++fails == 3) {
						throw new QuietException(e);
					}
					UtilitiesThread.sleepQuietly(100);
				}
			}
			return result;
		}

	}
}
