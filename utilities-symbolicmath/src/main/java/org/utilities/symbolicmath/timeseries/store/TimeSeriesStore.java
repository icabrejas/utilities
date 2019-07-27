package org.utilities.symbolicmath.timeseries.store;

import java.time.Instant;

public interface TimeSeriesStore {

	Instant time();

	Double get(String label);

	Double lag(String label, long lag);

	Double[] range(String label, long window);

}
