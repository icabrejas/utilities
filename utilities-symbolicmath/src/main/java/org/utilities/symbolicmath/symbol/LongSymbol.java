package org.utilities.symbolicmath.symbol;

import java.util.function.Function;

public interface LongSymbol<S> extends Symbol<S, Long> {

	static <S> LongSymbol<S> symbol(Function<S, Long> func) {
		if (func instanceof LongSymbol) {
			return (LongSymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> LongSymbol<S> constant(Long constant) {
		return store -> constant;
	}

}
