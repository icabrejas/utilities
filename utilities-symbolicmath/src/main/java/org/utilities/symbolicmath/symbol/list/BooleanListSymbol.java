package org.utilities.symbolicmath.symbol.list;

import java.util.List;
import java.util.function.Function;

import org.utilities.symbolicmath.symbol.array.BooleanArraySymbol;

public interface BooleanListSymbol<S> extends ListSymbol<S, Boolean> {

	static <S> BooleanListSymbol<S> symbol(Function<S, List<Boolean>> func) {
		if (func instanceof BooleanListSymbol) {
			return (BooleanListSymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	default BooleanArraySymbol<S> toArray() {
		return toArray(Boolean[]::new).asBooleanArray();
	}

}
