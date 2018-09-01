package org.utilities.symbolicmath.value.timeseries;

import org.utilities.symbolicmath.value.Value;

public class Lag implements Value<TimeSeriesStore, Double> {

	private String label;
	private long lag;

	public Lag(String label, long lag) {
		this.label = label;
		this.lag = lag;
	}

	@Override
	public Double apply(TimeSeriesStore store) {
		return store.get(label, -lag);
	}

}
