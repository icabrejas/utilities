package org.utilities.symbolicmath.symbol.array;

import java.util.function.Function;

import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.utils.StatisticsUtils;

public interface DoublePrimitiveArray2Symbol<S> extends Symbol<S, double[][]> {

	static <S> DoublePrimitiveArray2Symbol<S> symbol(Function<S, double[][]> func) {
		if (func instanceof DoublePrimitiveArray2Symbol) {
			return (DoublePrimitiveArray2Symbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> DoublePrimitiveArray2Symbol<S> constant(double[][] constant) {
		return store -> constant;
	}

	default DoublePrimitiveArray2Symbol<S> vectorialCovariance(boolean biasCorrected) {
		return andThen(x -> StatisticsUtils.vectorialCovariance(x, biasCorrected)).asDoublePrimitiveArray2();
	}

	default DoublePrimitiveArraySymbol<S> vectorialMean() {
		return andThen(StatisticsUtils::vectorialMean).asDoublePrimitiveArray();
	}

}
