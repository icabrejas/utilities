package org.utilities.symbolicmath;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.correlation.StorelessCovariance;
import org.apache.commons.math3.stat.descriptive.moment.VectorialCovariance;
import org.apache.commons.math3.stat.descriptive.moment.VectorialMean;

public class Statistics {

	public static class Correlation {

		public static double covariance(double[] x, double[] y, boolean biasCorrected) {
			return new Covariance().covariance(x, y, biasCorrected);
		}

		public static double kendallsCorrelation(double[] x, double[] y) {
			return new KendallsCorrelation().correlation(x, y);
		}

		public static double pearsonsCorrelation(double[] x, double[] y) {
			return new PearsonsCorrelation().correlation(x, y);
		}

		public static double spearmansCorrelation(double[] x, double[] y) {
			return new SpearmansCorrelation().correlation(x, y);
		}

		public static double storelessCovariance(double[] x, double[] y, int dim, boolean biasCorrected) {
			return new StorelessCovariance(dim, biasCorrected).covariance(x, y);
		}

	}

	public static class Moment {

		public static double[] vectorialMean(double[][] x) {
			VectorialMean vectorialMean = new VectorialMean(x[0].length);
			for (int i = 0; i < x.length; i++) {
				vectorialMean.increment(x[i]);
			}
			return vectorialMean.getResult();
		}

		public static RealMatrix vectorialCovariance(double[][] x, boolean biasCorrected) {
			VectorialCovariance vectorialCovariance = new VectorialCovariance(x[0].length, biasCorrected);
			for (int i = 0; i < x.length; i++) {
				vectorialCovariance.increment(x[i]);
			}
			return vectorialCovariance.getResult();
		}

	}

}
