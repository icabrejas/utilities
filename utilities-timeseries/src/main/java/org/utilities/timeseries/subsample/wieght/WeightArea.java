package org.utilities.timeseries.subsample.wieght;

import org.utilities.timeseries.SimpleEvent;

public class WeightArea<I> implements WeightCalculator<SimpleEvent<I>> {

	@Override
	public double calculate(SimpleEvent<I> prev, SimpleEvent<I> current, SimpleEvent<I> next) {
		double ax = prev.getTimeInMillis();
		double ay = prev.getDoubleValue();
		double bx = current.getTimeInMillis();
		double by = current.getDoubleValue();
		double cx = next.getTimeInMillis();
		double cy = next.getDoubleValue();
		return areaError(ax, ay, bx, by, cx, cy);
	}

	private double areaError(double ax, double ay, double bx, double by, double cx, double cy) {
		return Math.abs((bx - ax) * (cy - ay) - (by - ay) * (cx - ax));
	}

}
