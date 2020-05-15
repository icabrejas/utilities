package org.utilities.symbolicmath.store.symbol;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.utilities.symbolicmath.store.SymbolStoreTimeSeries;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.SymbolInstant;

// TODO move to timeseries
public class Time<T> implements SymbolInstant<SymbolStoreTimeSeries<T>> {

	@Override
	public Instant apply(SymbolStoreTimeSeries<T> store) {
		return store.time();
	}

	@Override
	public List<Symbol<SymbolStoreTimeSeries<T>, ?>> dependencies() {
		return Collections.emptyList();
	}

}
