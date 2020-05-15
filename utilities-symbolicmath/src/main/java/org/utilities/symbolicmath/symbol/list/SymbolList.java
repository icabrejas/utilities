package org.utilities.symbolicmath.symbol.list;

import java.util.List;
import java.util.function.Function;

import org.utilities.core.util.function.ArrayFactory;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.array.SymbolArray;

public interface SymbolList<S, V> extends Symbol<S, List<V>> {

	static <S, V> SymbolList<S, V> empty() {
		return constant(null);
	}

	static <S, V> SymbolList<S, V> constant(List<V> constant) {
		return s -> constant;
	}

	static <S, V> SymbolList<S, V> as(Function<S, List<V>> func) {
		if (func instanceof SymbolList) {
			return (SymbolList<S, V>) func;
		} else {
			return func::apply;
		}
	}

	default SymbolArray<S, V> toArray(ArrayFactory<V> factory) {
		return SymbolArray.as(andThenNotNullEval(x -> x.toArray(factory.create(x.size()))));
	}

}
