package org.utilities.core.util.function;

import java.util.function.Consumer;

public interface ConsumerPlus<T> extends Consumer<T> {

	public static <T> ConsumerPlus<T> dummy(Class<T> type) {
		return dummy();
	}

	public static <T> ConsumerPlus<T> dummy() {
		return (t) -> {
		};
	}

	public static <T> ConsumerPlus<T> newInstance(Consumer<T> consumer) {
		return consumer::accept;
	}

	public static <T> FunctionPlus<T, T> parseFunction(Consumer<T> consumer) {
		return t -> {
			consumer.accept(t);
			return t;
		};
	}

	default FunctionPlus<T, T> parseFunction() {
		return ConsumerPlus.parseFunction(this);
	}

}
