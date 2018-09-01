package org.utilities.timeseries.subsample.wieght;

import org.utilities.timeseries.SimpleEvent;

public class WeightLinear<I> implements WeightCalculator<SimpleEvent<I>> {

	@Override
	public double calculate(SimpleEvent<I> prev, SimpleEvent<I> current, SimpleEvent<I> next) {
		double ax = prev.getTimeInMillis();
		double ay = prev.getDoubleValue();
		double bx = current.getTimeInMillis();
		double by = current.getDoubleValue();
		double cx = next.getTimeInMillis();
		double cy = next.getDoubleValue();
		return linearError(ax, ay, bx, by, cx, cy);
	}

	private double linearError(double ax, double ay, double bx, double by, double cx, double cy) {
		return Math.abs(by - (ay + (bx - ax) * (cy - ay) / (cx - ax)));
	}
	
}
