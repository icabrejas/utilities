package org.utilities.core.util.function;

import java.util.function.BiPredicate;

public interface BiPredicatePlus<T, U> extends BiPredicate<T, U> {

	public static <T, U> BiPredicatePlus<T, U> dummy() {
		return (t1, t2) -> true;
	}

	public static <T, U> BiPredicatePlus<T, U> newInstance(BiPredicate<T, U> biPredicate) {
		return biPredicate::test;
	}

	public static <T, U> PredicatePlus<T> parsePredicate(BiPredicate<T, U> biPredicate, U u) {
		return t -> biPredicate.test(t, u);
	}

	default PredicatePlus<T> parsePredicate(U u) {
		return BiPredicatePlus.parsePredicate(this, u);
	}

	public static <T, U> BiPredicatePlus<U, T> flip(BiPredicate<T, U> biPredicate) {
		return (u, t) -> biPredicate.test(t, u);
	}

	default BiPredicatePlus<U, T> flip() {
		return BiPredicatePlus.flip(this);
	}
}
