package org.utilities.symbolicmath.timeseries.symbol;

import org.utilities.symbolicmath.symbol.DoubleSymbol;
import org.utilities.symbolicmath.timeseries.store.TimeSeriesStore;

public class Lag<S extends TimeSeriesStore> implements DoubleSymbol<S> {

	private String label;
	private long lag;

	public Lag(String label, long lag) {
		this.label = label;
		this.lag = lag;
	}

	@Override
	public Double apply(TimeSeriesStore store) {
		return store.lag(label, lag);
	}

}
