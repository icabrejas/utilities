package org.utilities.core.lang.iterable;

import java.util.function.Function;
import java.util.function.Supplier;

import org.utilities.core.util.function.FunctionPlus;

public interface Entry<I, T> {

	I getInfo();

	T getContent();

	public static <I, T> Entry<I, T> from(Supplier<I> info, Supplier<T> content) {
		return new Entry<I, T>() {

			@Override
			public I getInfo() {
				return info.get();
			}

			@Override
			public T getContent() {
				return content.get();
			}
		};
	}

	public static <I, T> Entry<I, T> newInstance(Supplier<I> info, T content) {
		return from(info, () -> content);
	}

	public static <I, T> Entry<I, T> newInstance(I info, Supplier<T> content) {
		return from(() -> info, content);
	}

	public static <I, T> Entry<I, T> newInstance(I info, T content) {
		return from(() -> info, () -> content);
	}

	public static <I, T1, T2> Function<Entry<I, T1>, Entry<I, T2>> entryMapper(Function<T1, T2> content) {
		return entryMapper(FunctionPlus.newInstance(content));
	}

	public static <I, T1, T2> Function<Entry<I, T1>, Entry<I, T2>> entryMapper(FunctionPlus<T1, T2> content) {
		return entry -> Entry.newInstance(entry.getInfo(), content.parseSupplier(entry.getContent()));
	}

	public static <I1, T1, I2, T2> Function<Entry<I1, T1>, Entry<I2, T2>> entryMapper(Function<I1, I2> info,
			Function<T1, T2> content) {
		return entryMapper(FunctionPlus.newInstance(info), FunctionPlus.newInstance(content));
	}

	public static <I1, T1, I2, T2> Function<Entry<I1, T1>, Entry<I2, T2>> entryMapper(FunctionPlus<I1, I2> info,
			FunctionPlus<T1, T2> content) {
		return entry -> Entry.from(info.parseSupplier(entry.getInfo()),
				content.parseSupplier(entry.getContent()));
	}

	public static <T, I, R> Function<T, Entry<I, R>> mapper(Function<T, I> info, Function<T, R> content) {
		return mapper(FunctionPlus.newInstance(info), FunctionPlus.newInstance(content));
	}

	public static <T, I, R> Function<T, Entry<I, R>> mapper(FunctionPlus<T, I> info, FunctionPlus<T, R> content) {
		return t -> Entry.from(info.parseSupplier(t), content.parseSupplier(t));
	}

}
