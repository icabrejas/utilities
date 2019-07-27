package org.utilities.symbolicmath.symbol.list;

import java.util.List;
import java.util.function.Function;

import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.array.ArraySymbol;

public interface ListSymbol<S, V> extends Symbol<S, List<V>> {

	static <S, V> ListSymbol<S, V> symbol(Function<S, List<V>> func) {
		if (func instanceof ListSymbol) {
			return (ListSymbol<S, V>) func;
		} else {
			return func::apply;
		}
	}

	default ArraySymbol<S, V> toArray(Function<Integer, V[]> factory) {
		return andThen(x -> x.toArray(factory.apply(x.size()))).asArray();
	}

}
