package org.utilities.timeseries.subsample.wieght;

import org.utilities.timeseries.Event;

public class WeightArea implements WeightCalculator<Event> {

	private String signal;

	public WeightArea(String signal) {
		this.signal = signal;
	}

	@Override
	public double calculate(Event prev, Event current, Event next) {
		long ax = prev.getTime()
				.toEpochMilli();
		double ay = prev.getDouble(signal);
		long bx = current.getTime()
				.toEpochMilli();
		double by = current.getDouble(signal);
		long cx = next.getTime()
				.toEpochMilli();
		double cy = next.getDouble(signal);
		return areaError(ax, ay, bx, by, cx, cy);
	}

	private double areaError(double ax, double ay, double bx, double by, double cx, double cy) {
		return Math.abs((bx - ax) * (cy - ay) - (by - ay) * (cx - ax));
	}

}
