package org.utilities.symbolicmath.symbol.list;

import java.util.List;
import java.util.function.Function;

import org.utilities.symbolicmath.symbol.array.SymbolArrayBoolean;

public interface SymbolListBoolean<S> extends SymbolList<S, Boolean> {

	static <S> SymbolListBoolean<S> empty() {
		return constant(null);
	}

	static <S> SymbolListBoolean<S> constant(List<Boolean> constant) {
		return s -> constant;
	}

	static <S> SymbolListBoolean<S> as(Function<S, List<Boolean>> func) {
		if (func instanceof SymbolListBoolean) {
			return (SymbolListBoolean<S>) func;
		} else {
			return func::apply;
		}
	}

	default SymbolArrayBoolean<S> toArray() {
		return SymbolArrayBoolean.as(toArray(Boolean[]::new));
	}

}
