package org.utilities.symbolicmath.timeseries.symbol;

import org.utilities.symbolicmath.symbol.array.DoubleArraySymbol;
import org.utilities.symbolicmath.timeseries.store.TimeSeriesStore;

public class Range<S extends TimeSeriesStore> implements DoubleArraySymbol<S> {

	private String label;
	private long window;

	public Range(String label, long window) {
		this.label = label;
		this.window = window;
	}

	@Override
	public Double[] apply(TimeSeriesStore store) {
		return store.range(label, window);
	}

}
