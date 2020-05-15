package org.utilities.symbolicmath.utils;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.stat.descriptive.moment.VectorialCovariance;
import org.apache.commons.math3.stat.descriptive.moment.VectorialMean;

public class StatisticsUtils {

	public static double[] vectorialMean(double[][] x) {
		VectorialMean vectorialMean = new VectorialMean(x[0].length);
		for (int i = 0; i < x.length; i++) {
			vectorialMean.increment(x[i]);
		}
		return vectorialMean.getResult();
	}

	public static double[][] vectorialCovariance(double[][] x, boolean biasCorrected) {
		VectorialCovariance vectorialCovariance = new VectorialCovariance(x[0].length, biasCorrected);
		for (int i = 0; i < x.length; i++) {
			vectorialCovariance.increment(x[i]);
		}
		return vectorialCovariance.getResult()
				.getData();
	}

	public static PolynomialSplineFunction linearInterpolator(double[] x, double[] y) {
		return new LinearInterpolator().interpolate(x, y);
	}

}
