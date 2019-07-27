package org.utilities.symbolicmath.timeseries.symbol;

import java.time.Instant;

import org.utilities.symbolicmath.symbol.InstantSymbol;
import org.utilities.symbolicmath.timeseries.store.TimeSeriesStore;

public class Time<S extends TimeSeriesStore> implements InstantSymbol<S> {

	@Override
	public Instant apply(S store) {
		return store.time();
	}

}
