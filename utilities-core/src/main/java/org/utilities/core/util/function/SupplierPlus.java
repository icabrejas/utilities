package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utilities.core.UtilitiesConcurrent;
import org.utilities.core.lang.exception.QuietException;

public interface SupplierPlus<T> extends Supplier<T> {

	public static <T> SupplierPlus<T> dummy() {
		return newInstance(null);
	}

	public static <T> SupplierPlus<T> newInstance(Supplier<T> supplier) {
		return supplier::get;
	}

	public static <T> SupplierPlus<T> newInstance(T t) {
		return () -> t;
	}

	public static <T, U, R> SupplierPlus<R> andThen(Supplier<T> supplier, BiFunction<T, U, R> after, U u) {
		Objects.requireNonNull(after);
		return () -> after.apply(supplier.get(), u);
	}

	public static <T, R> SupplierPlus<R> andThen(Supplier<T> supplier, Function<T, R> after) {
		Objects.requireNonNull(after);
		return () -> after.apply(supplier.get());
	}

	public default <R> SupplierPlus<R> andThen(Function<T, R> after) {
		return SupplierPlus.andThen(this, after);
	}

	public default <U, R> SupplierPlus<R> map(BiFunction<T, U, R> mapper, U u) {
		return SupplierPlus.andThen(this, mapper, u);
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

		static final Logger LOG = LoggerFactory.getLogger(SupplierPlus.Noisy.class);

		T get() throws Exception;

		public static <T> T tryToGet(SupplierPlus.Noisy<T> supplier, int trials, long waitTime) {
			int fails = 0;
			T result = null;
			while (result == null) {
				try {
					result = supplier.get();
				} catch (Exception e) {
					LOG.warn("fail " + (fails + 1) + "/" + trials);
					if (++fails == 3) {
						throw new QuietException(e);
					}
					UtilitiesConcurrent.sleepQuietly(waitTime);
				}
			}
			return result;
		}

	}
}
