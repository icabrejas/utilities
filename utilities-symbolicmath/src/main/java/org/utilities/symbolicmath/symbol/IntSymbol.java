package org.utilities.symbolicmath.symbol;

import java.util.function.Function;

public interface IntSymbol<S> extends Symbol<S, Integer> {

	static <S> IntSymbol<S> symbol(Function<S, Integer> func) {
		if (func instanceof IntSymbol) {
			return (IntSymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> IntSymbol<S> constant(Integer constant) {
		return store -> constant;
	}

}
