package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface PredicatePlus<T> extends Predicate<T> {

	public static <T> PredicatePlus<T> dummy() {
		return t -> true;
	}

	public static <T> PredicatePlus<T> newInstance(Predicate<T> predicate) {
		return predicate::test;
	}

	default ConsumerPlus<T> ifThen(Consumer<T> consumer) {
		return ifThen(this, consumer);
	}

	static <T> ConsumerPlus<T> ifThen(Predicate<T> predicate, Consumer<T> consumer) {
		return t -> {
			if (predicate.test(t)) {
				consumer.accept(t);
			}
		};
	}

	@Override
	default PredicatePlus<T> and(Predicate<? super T> other) {
		return Predicate.super.and(other)::test;
	}

	static <T> PredicatePlus<T> and(Predicate<T> a, Predicate<T> b) {
		return a.and(b)::test;
	}

	@Override
	default PredicatePlus<T> negate() {
		return Predicate.super.negate()::test;
	}

	static <T> PredicatePlus<T> negate(Predicate<T> predicate) {
		return predicate.negate()::test;
	}

	@Override
	default PredicatePlus<T> or(Predicate<? super T> other) {
		return Predicate.super.or(other)::test;
	}

	static <T> PredicatePlus<T> or(Predicate<T> a, Predicate<T> b) {
		return a.or(b)::test;
	}

	static <T> PredicatePlus<T> isEqual(Object targetRef) {
		return Predicate.isEqual(targetRef)::test;
	}

	static <T> PredicatePlus<T> isNull() {
		return Objects::isNull;
	}

	static <T> PredicatePlus<T> isNotNull() {
		return negate(isNull());
	}

}
