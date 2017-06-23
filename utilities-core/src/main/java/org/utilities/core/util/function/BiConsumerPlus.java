package org.utilities.core.util.function;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface BiConsumerPlus<T, U> extends BiConsumer<T, U> {

	public static <T, U> BiConsumerPlus<T, U> newInstance(BiConsumer<T, U> biConsumer) {
		return biConsumer::accept;
	}

	public static <T, U> ConsumerPlus<T> parseConsumer(BiConsumer<T, U> biConsumer, U u) {
		return t -> biConsumer.accept(t, u);
	}

	public static <T, K, V> ConsumerPlus<T> parseConsumer(BiConsumer<K, V> biConsumer, Function<T, K> key,
			Function<T, V> value) {
		return t -> biConsumer.accept(key.apply(t), value.apply(t));
	}

	default ConsumerPlus<T> parseConsumer(U u) {
		return BiConsumerPlus.parseConsumer(this, u);
	}

	public static <T, U> BiConsumerPlus<U, T> flip(BiConsumer<T, U> biConsumer) {
		return (u, t) -> biConsumer.accept(t, u);
	}

	default BiConsumerPlus<U, T> flip() {
		return BiConsumerPlus.flip(this);
	}

	public static <T, U> BiConsumerPlus<T, U> dummy(Class<T> typeT, Class<U> typeU) {
		return (t, u) -> {
		};
	}

	public static <T, U> BiConsumerPlus<T, U> dummy() {
		return (t, u) -> {
		};
	}

}
