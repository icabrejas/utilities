package org.utilities.symbolicmath.symbol.list;

import java.util.List;
import java.util.function.Function;

import org.utilities.symbolicmath.symbol.array.DoubleArraySymbol;

public interface DoubleListSymbol<S> extends ListSymbol<S, Double> {

	static <S> DoubleListSymbol<S> symbol(Function<S, List<Double>> func) {
		if (func instanceof DoubleListSymbol) {
			return (DoubleListSymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	default DoubleArraySymbol<S> toArray() {
		return toArray(Double[]::new).asDoubleArray();
	}

}
