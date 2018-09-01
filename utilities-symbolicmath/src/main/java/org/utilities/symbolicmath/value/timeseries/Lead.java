package org.utilities.symbolicmath.value.timeseries;

import org.utilities.symbolicmath.value.Value;

public class Lead implements Value<TimeSeriesStore, Double> {

	private String label;
	private long lead;

	public Lead(String label, long lead) {
		this.label = label;
		this.lead = lead;
	}

	@Override
	public Double apply(TimeSeriesStore store) {
		return store.get(label, lead);
	}

}
