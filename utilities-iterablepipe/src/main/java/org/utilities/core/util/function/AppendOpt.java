package org.utilities.core.util.function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public enum AppendOpt {

	Before, After;

	public <T> Consumer<T> append(Consumer<T> old, Consumer<T> current) {
		return AppendOpt.append(old, current, this);
	}

	public static <T> Consumer<T> append(Consumer<T> old, Consumer<T> current, AppendOpt append) {
		switch (append) {
		case Before:
			return current.andThen(old);
		case After:
		default:
			return old.andThen(current);
		}
	}

	public <T, U> BiConsumer<T, U> append(BiConsumer<T, U> old, BiConsumer<T, U> current) {
		return AppendOpt.append(old, current, this);
	}

	public static <T, U> BiConsumer<T, U> append(BiConsumer<T, U> old, BiConsumer<T, U> current, AppendOpt append) {
		switch (append) {
		case Before:
			return current.andThen(old);
		case After:
		default:
			return old.andThen(current);
		}
	}

}
