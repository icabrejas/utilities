package org.utilities.symbolicmath.store;

import java.time.Instant;

public interface SymbolStoreTimeSeries<T> extends SymbolStore<T> {

	Instant time();

}
