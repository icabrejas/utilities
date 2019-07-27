package org.utilities.symbolicmath.timeseries.store.cache;

public interface Cache {

	void put(long i, Double value);

	Double get(long i);

	Double[] get(long from, long to);

	void clear();

}
