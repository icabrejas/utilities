package org.utilities.symbolicmath.symbol.list;

import java.util.List;
import java.util.function.Function;

import org.utilities.symbolicmath.symbol.array.SymbolArrayDouble;

public interface SymbolListDouble<S> extends SymbolList<S, Double> {

	static <S> SymbolListDouble<S> empty() {
		return constant(null);
	}

	static <S> SymbolListDouble<S> constant(List<Double> constant) {
		return s -> constant;
	}

	static <S> SymbolListDouble<S> as(Function<S, List<Double>> func) {
		if (func instanceof SymbolListDouble) {
			return (SymbolListDouble<S>) func;
		} else {
			return func::apply;
		}
	}

	default SymbolArrayDouble<S> toArray() {
		return SymbolArrayDouble.as(toArray(Double[]::new));
	}

}
