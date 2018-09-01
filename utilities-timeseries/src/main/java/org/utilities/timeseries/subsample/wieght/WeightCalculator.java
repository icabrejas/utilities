package org.utilities.timeseries.subsample.wieght;

public interface WeightCalculator<V> {

	double calculate(V prev, V current, V next);
	
}
