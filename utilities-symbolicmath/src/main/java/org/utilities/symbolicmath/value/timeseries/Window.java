package org.utilities.symbolicmath.value.timeseries;

import java.util.List;

import org.utilities.symbolicmath.value.Value;

public class Window implements Value<TimeSeriesStore, List<Double>> {

	private String label;
	private long from;
	private long to;

	public Window(String label, long window, Align align) {
		this.label = label;
		switch (align) {
		case LEFT:
			from = 0;
			to = window;
			break;
		case CENTER:
			from = -window / 2;
			to = window / 2;
			break;
		case RIGHT:
			from = -window;
			to = 0;
			break;
		default:
			break;
		}
	}

	@Override
	public List<Double> apply(TimeSeriesStore store) {
		return store.get(label, from, to);
	}

	public static enum Align {
		LEFT, CENTER, RIGHT
	}

}
