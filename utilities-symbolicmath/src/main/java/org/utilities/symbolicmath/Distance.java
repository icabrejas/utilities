package org.utilities.symbolicmath;

import org.apache.commons.math3.ml.distance.CanberraDistance;
import org.apache.commons.math3.ml.distance.ChebyshevDistance;
import org.apache.commons.math3.ml.distance.EarthMoversDistance;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;

public class Distance {

	public static double manhattanDistance(double[] x, double[] y) {
		return new ManhattanDistance().compute(x, y);
	}

	public static double canberraDistance(double[] x, double[] y) {
		return new CanberraDistance().compute(x, y);
	}

	public static double chebyshevDistance(double[] x, double[] y) {
		return new ChebyshevDistance().compute(x, y);
	}

	public static double earthMoversDistance(double[] x, double[] y) {
		return new EarthMoversDistance().compute(x, y);
	}

	public static double euclideanDistance(double[] x, double[] y) {
		return new EuclideanDistance().compute(x, y);
	}
}
