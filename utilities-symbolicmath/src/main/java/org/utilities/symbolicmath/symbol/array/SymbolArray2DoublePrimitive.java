package org.utilities.symbolicmath.symbol.array;

import java.util.function.Function;

import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.utils.StatisticsUtils;

public interface SymbolArray2DoublePrimitive<S> extends Symbol<S, double[][]> {

	static <S> SymbolArray2DoublePrimitive<S> empty() {
		return constant(null);
	}

	static <S> SymbolArray2DoublePrimitive<S> constant(double[][] constant) {
		return s -> constant;
	}

	static <S> SymbolArray2DoublePrimitive<S> as(Function<S, double[][]> func) {
		if (func instanceof SymbolArray2DoublePrimitive) {
			return (SymbolArray2DoublePrimitive<S>) func;
		} else {
			return func::apply;
		}
	}

	default SymbolArray2DoublePrimitive<S> vectorialCovariance(Symbol<S, Boolean> biasCorrected) {
		return as(andThenNotNullEval(StatisticsUtils::vectorialCovariance, biasCorrected));
	}

	default SymbolArrayDoublePrimitive<S> vectorialMean() {
		return SymbolArrayDoublePrimitive.as(andThen(StatisticsUtils::vectorialMean));
	}

}
