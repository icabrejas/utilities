package org.utilities.symbolicmath.value.timeseries;

import java.util.List;

public interface TimeSeriesStore {

	Double get(String label);

	Double get(String label, long lag);

	List<Double> get(String label, long from, long to);

}
