package org.utilities.symbolicmath.symbol;

import java.util.function.Function;

public interface FloatSymbol<S> extends Symbol<S, Float> {

	static <S> FloatSymbol<S> symbol(Function<S, Float> func) {
		if (func instanceof FloatSymbol) {
			return (FloatSymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> FloatSymbol<S> constant(Float constant) {
		return store -> constant;
	}

}
