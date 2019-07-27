package org.utilities.symbolicmath.symbol;

import java.util.function.Function;

public interface StringSymbol<S> extends Symbol<S, String> {

	static <S> StringSymbol<S> symbol(Function<S, String> func) {
		if (func instanceof StringSymbol) {
			return (StringSymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> StringSymbol<S> constant(String constant) {
		return store -> constant;
	}

}
