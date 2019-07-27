package org.utilities.symbolicmath.timeseries.symbol;

import org.utilities.symbolicmath.symbol.DoubleSymbol;
import org.utilities.symbolicmath.timeseries.store.TimeSeriesStore;

public class Current<S extends TimeSeriesStore> implements DoubleSymbol<S> {

	private String label;

	public Current(String label) {
		this.label = label;
	}

	@Override
	public Double apply(TimeSeriesStore store) {
		return store.get(label);
	}

}
