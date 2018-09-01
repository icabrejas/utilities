package org.utilities.symbolicmath.value.timeseries;

import org.utilities.symbolicmath.value.Value;

public class Current implements Value<TimeSeriesStore, Double> {

	private String label;

	public Current(String label) {
		this.label = label;
	}

	@Override
	public Double apply(TimeSeriesStore store) {
		return store.get(label);
	}
}
