package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface PredicatePlus<T> extends Predicate<T> {

	public static <T> PredicatePlus<T> newInstance(Predicate<T> predicate) {
		return predicate::test;
	}

	public static <T> PredicatePlus<T> negate(Predicate<T> predicate) {
		return predicate.negate()::test;
	}

	default ConsumerPlus<T> ifThen(Consumer<T> consumer) {
		return t -> {
			if (test(t)) {
				consumer.accept(t);
			}
		};
	}

	public static <T> Predicate<T> isNull() {
		return Objects::isNull;
	}

	public static <T> Predicate<T> isNotNull() {
		return negate(isNull());
	}

}
