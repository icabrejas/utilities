package org.utilities.symbolicmath.store.rolling;

import java.util.function.Function;

import org.utilities.core.UtilitiesFunction;
import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.store.symbol.Range;
import org.utilities.symbolicmath.symbol.SymbolDouble;
import org.utilities.symbolicmath.symbol.array.SymbolArrayDouble;
import org.utilities.symbolicmath.symbol.array.SymbolArrayDoublePrimitive;

public class RollingUtils {

	public static SymbolDouble<SymbolStore<Double>> summarize(String label, int window,
			SummarizerDouble<Double> summarizer) {
		return summarize(label, window, true, summarizer);
	}

	public static SymbolDouble<SymbolStore<Double>> summarize(String label, int window, boolean nullOmit,
			SummarizerDouble<Double> summarizer) {
		return summarize(label, window, nullOmit, x -> (Double) x, summarizer);
	}

	public static <T> SymbolDouble<SymbolStore<T>> summarize(String label, int window, boolean nullOmit,
			Function<T, Double> parseDouble, SummarizerDouble<T> summarizer) {
		SymbolArrayDoublePrimitive<SymbolStore<T>> symbol = new Range<T>(label, window)//
				.andThenForeach(UtilitiesFunction.applyIfNotNull(parseDouble), Double[]::new)
				.apply(SymbolArrayDouble::as)
				.toPrimitive(nullOmit);
		return summarizer.apply(symbol);
	}

}
