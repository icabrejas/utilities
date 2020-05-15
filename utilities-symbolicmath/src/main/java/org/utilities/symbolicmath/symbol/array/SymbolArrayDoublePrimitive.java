package org.utilities.symbolicmath.symbol.array;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

import org.apache.commons.math3.analysis.function.Abs;
import org.apache.commons.math3.analysis.function.Acos;
import org.apache.commons.math3.analysis.function.Acosh;
import org.apache.commons.math3.analysis.function.Add;
import org.apache.commons.math3.analysis.function.Asin;
import org.apache.commons.math3.analysis.function.Asinh;
import org.apache.commons.math3.analysis.function.Atan;
import org.apache.commons.math3.analysis.function.Atan2;
import org.apache.commons.math3.analysis.function.Atanh;
import org.apache.commons.math3.analysis.function.Ceil;
import org.apache.commons.math3.analysis.function.Cos;
import org.apache.commons.math3.analysis.function.Cosh;
import org.apache.commons.math3.analysis.function.Divide;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.commons.math3.analysis.function.Expm1;
import org.apache.commons.math3.analysis.function.Floor;
import org.apache.commons.math3.analysis.function.Inverse;
import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.analysis.function.Log10;
import org.apache.commons.math3.analysis.function.Log1p;
import org.apache.commons.math3.analysis.function.Minus;
import org.apache.commons.math3.analysis.function.Multiply;
import org.apache.commons.math3.analysis.function.Pow;
import org.apache.commons.math3.analysis.function.Power;
import org.apache.commons.math3.analysis.function.Signum;
import org.apache.commons.math3.analysis.function.Sin;
import org.apache.commons.math3.analysis.function.Sinh;
import org.apache.commons.math3.analysis.function.Sqrt;
import org.apache.commons.math3.analysis.function.Subtract;
import org.apache.commons.math3.analysis.function.Tan;
import org.apache.commons.math3.analysis.function.Tanh;
import org.apache.commons.math3.ml.distance.CanberraDistance;
import org.apache.commons.math3.ml.distance.ChebyshevDistance;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.ml.distance.EarthMoversDistance;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.correlation.StorelessCovariance;
import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.SemiVariance;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.stat.descriptive.rank.Min;
import org.apache.commons.math3.stat.descriptive.rank.PSquarePercentile;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.commons.math3.stat.descriptive.summary.SumOfLogs;
import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares;
import org.apache.commons.math3.util.MathArrays;
import org.utilities.core.UtilitiesArray;
import org.utilities.core.util.function.ArrayFactory;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.SymbolDouble;
import org.utilities.symbolicmath.utils.LogicalUtils;

public interface SymbolArrayDoublePrimitive<S> extends Symbol<S, double[]> {

	static <S> SymbolArrayDoublePrimitive<S> empty() {
		return constant(null);
	}

	static <S> SymbolArrayDoublePrimitive<S> constant(double[] constant) {
		return s -> constant;
	}

	static <S> SymbolArrayDoublePrimitive<S> as(Function<S, double[]> func) {
		if (func instanceof SymbolArrayDoublePrimitive) {
			return (SymbolArrayDoublePrimitive<S>) func;
		} else {
			return func::apply;
		}
	}

	default <R> SymbolArray<S, R> andThenForeach(DoubleFunction<R> after, ArrayFactory<R> factory) {
		return SymbolArray.as(andThenNotNullEval(v -> UtilitiesArray.mapAll(v, after, factory)));
	}

	default <R> SymbolArray<S, R> andThenNotNullEvalForeach(BiFunction<Double, Double, R> after, Symbol<S, double[]> y,
			ArrayFactory<R> factory) {
		return SymbolArray.as(andThenNotNullEval((v, w) -> UtilitiesArray.mapAll(v, w, after, factory), y));
	}

	default SymbolArrayDoublePrimitive<S> andThenNotNullEvalForeach(DoubleUnaryOperator after) {
		return SymbolArrayDoublePrimitive.as(andThenNotNullEval(v -> UtilitiesArray.mapAll(v, after)));
	}

	default SymbolArrayDoublePrimitive<S> andThenNotNullEvalForeach(DoubleBinaryOperator after, Symbol<S, double[]> y) {
		return as(andThenNotNullEval((v, w) -> UtilitiesArray.mapAll(v, w, after), y));
	}

	default SymbolArrayBooleanPrimitive<S> andThenNotNullEvalForeach(DoublePredicate after) {
		return SymbolArrayBooleanPrimitive.as(andThenNotNullEval(v -> UtilitiesArray.mapAll(v, after)));
	}

	default SymbolArrayBooleanPrimitive<S> andThenNotNullEvalForeach(BiPredicate<Double, Double> after,
			Symbol<S, double[]> y) {
		return SymbolArrayBooleanPrimitive.as(andThenNotNullEval((v, w) -> UtilitiesArray.mapAll(v, w, after), y));
	}

	default SymbolDouble<S> andThenDistanceMeasure(DistanceMeasure after, Symbol<S, double[]> y) {
		return SymbolDouble.as(andThenNotNullEval(after::compute, y));
	}

	default SymbolDouble<S> andThenMathArraysFunction(MathArrays.Function after) {
		return SymbolDouble.as(andThenNotNullEval(after::evaluate));
	}

	default SymbolArrayDoublePrimitive<S> abs() {
		return andThenNotNullEvalForeach(new Abs()::value);
	}

	default SymbolArrayDoublePrimitive<S> add(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(new Add()::value, y);
	}

	default SymbolArrayDoublePrimitive<S> ceil() {
		return andThenNotNullEvalForeach(new Ceil()::value);
	}

	default SymbolArrayDoublePrimitive<S> divide(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(new Divide()::value, y);
	}

	default SymbolArrayDoublePrimitive<S> exp() {
		return andThenNotNullEvalForeach(new Exp()::value);
	}

	default SymbolArrayDoublePrimitive<S> expm1() {
		return andThenNotNullEvalForeach(new Expm1()::value);
	}

	default SymbolArrayDoublePrimitive<S> floor() {
		return andThenNotNullEvalForeach(new Floor()::value);
	}

	default SymbolArrayDoublePrimitive<S> inverse() {
		return andThenNotNullEvalForeach(new Inverse()::value);
	}

	default SymbolArrayDoublePrimitive<S> log() {
		return andThenNotNullEvalForeach(new Log()::value);
	}

	default SymbolArrayDoublePrimitive<S> log10() {
		return andThenNotNullEvalForeach(new Log10()::value);
	}

	default SymbolArrayDoublePrimitive<S> log1p() {
		return andThenNotNullEvalForeach(new Log1p()::value);
	}

	default SymbolArrayDoublePrimitive<S> minus() {
		return andThenNotNullEvalForeach(new Minus()::value);
	}

	default SymbolArrayDoublePrimitive<S> multiply(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(new Multiply()::value, y);
	}

	default SymbolArrayDoublePrimitive<S> pow(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(new Pow()::value, y);
	}

	default SymbolArrayDoublePrimitive<S> pow(double p) {
		return andThenNotNullEvalForeach(new Power(p)::value);
	}

	default SymbolArrayDoublePrimitive<S> root(Symbol<S, double[]> y) {
		return pow(as(y).inverse());
	}

	default SymbolArrayDoublePrimitive<S> signum() {
		return andThenNotNullEvalForeach(new Signum()::value);
	}

	default SymbolArrayDoublePrimitive<S> sqrt() {
		return andThenNotNullEvalForeach(new Sqrt()::value);
	}

	default SymbolArrayDoublePrimitive<S> subtract(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(new Subtract()::value, y);
	}

	default SymbolArrayBooleanPrimitive<S> allEquals(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(LogicalUtils::equals, y);
	}

	default SymbolArrayBooleanPrimitive<S> greater(boolean extrict, Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(extrict ? LogicalUtils::greater : LogicalUtils::greaterOrEquals, y);
	}

	default SymbolArrayBooleanPrimitive<S> lower(boolean extrict, Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(extrict ? LogicalUtils::lower : LogicalUtils::lowerOrEquals, y);
	}

	default SymbolArrayDoublePrimitive<S> max(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(new org.apache.commons.math3.analysis.function.Max()::value, y);
	}

	default SymbolArrayDoublePrimitive<S> min(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(new org.apache.commons.math3.analysis.function.Min()::value, y);
	}

	default SymbolArrayBooleanPrimitive<S> allNotEquals(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(LogicalUtils::notEquals, y);
	}

	default SymbolArrayBooleanPrimitive<S> toLogical() {
		return andThenNotNullEvalForeach(LogicalUtils::parseBoolean);
	}

	default SymbolArrayDoublePrimitive<S> acos() {
		return andThenNotNullEvalForeach(new Acos()::value);
	}

	default SymbolArrayDoublePrimitive<S> acosh() {
		return andThenNotNullEvalForeach(new Acosh()::value);
	}

	default SymbolArrayDoublePrimitive<S> asin() {
		return andThenNotNullEvalForeach(new Asin()::value);
	}

	default SymbolArrayDoublePrimitive<S> asinh() {
		return andThenNotNullEvalForeach(new Asinh()::value);
	}

	default SymbolArrayDoublePrimitive<S> atan() {
		return andThenNotNullEvalForeach(new Atan()::value);
	}

	default SymbolArrayDoublePrimitive<S> atan2(Symbol<S, double[]> y) {
		return andThenNotNullEvalForeach(new Atan2()::value, y);
	}

	default SymbolArrayDoublePrimitive<S> atanh() {
		return andThenNotNullEvalForeach(new Atanh()::value);
	}

	default SymbolArrayDoublePrimitive<S> cos() {
		return andThenNotNullEvalForeach(new Cos()::value);
	}

	default SymbolArrayDoublePrimitive<S> cosh() {
		return andThenNotNullEvalForeach(new Cosh()::value);
	}

	default SymbolArrayDoublePrimitive<S> sin() {
		return andThenNotNullEvalForeach(new Sin()::value);
	}

	default SymbolArrayDoublePrimitive<S> sinh() {
		return andThenNotNullEvalForeach(new Sinh()::value);
	}

	default SymbolArrayDoublePrimitive<S> tan() {
		return andThenNotNullEvalForeach(new Tan()::value);
	}

	default SymbolArrayDoublePrimitive<S> tanh() {
		return andThenNotNullEvalForeach(new Tanh()::value);
	}

	default SymbolArrayDoublePrimitive<S> toDegrees() {
		return andThenNotNullEvalForeach(Math::toDegrees);
	}

	default SymbolArrayDoublePrimitive<S> toRadians() {
		return andThenNotNullEvalForeach(Math::toRadians);
	}

	default SymbolDouble<S> canberraDistance(Symbol<S, double[]> y) {
		return andThenDistanceMeasure(new CanberraDistance(), y);
	}

	default SymbolDouble<S> chebyshevDistance(Symbol<S, double[]> y) {
		return andThenDistanceMeasure(new ChebyshevDistance(), y);
	}

	default SymbolDouble<S> earthMoversDistance(Symbol<S, double[]> y) {
		return andThenDistanceMeasure(new EarthMoversDistance(), y);
	}

	default SymbolDouble<S> euclideanDistance(Symbol<S, double[]> y) {
		return andThenDistanceMeasure(new EuclideanDistance(), y);
	}

	default SymbolDouble<S> manhattanDistance(Symbol<S, double[]> y) {
		return andThenDistanceMeasure(new ManhattanDistance(), y);
	}

	default SymbolDouble<S> covariance(Symbol<S, double[]> y) {
		return SymbolDouble.as(andThenNotNullEval(new Covariance()::covariance, y));
	}

	default SymbolDouble<S> covariance(Symbol<S, double[]> y, boolean biasCorrected) {
		return SymbolDouble.as(andThenNotNullEval((a, b) -> new Covariance().covariance(a, b, biasCorrected), y));
	}

	default SymbolDouble<S> storelessCovariance(Symbol<S, double[]> y, int dim) {
		return SymbolDouble.as(andThenNotNullEval(new StorelessCovariance(dim)::covariance, y));
	}

	default SymbolDouble<S> storelessCovariance(Symbol<S, double[]> y, int dim, boolean biasCorrected) {
		return SymbolDouble.as(andThenNotNullEval(new StorelessCovariance(dim, biasCorrected)::covariance, y));
	}

	default SymbolDouble<S> kendallsCorrelation(Symbol<S, double[]> y) {
		return SymbolDouble.as(andThenNotNullEval(new KendallsCorrelation()::correlation, y));
	}

	default SymbolDouble<S> pearsonsCorrelation(Symbol<S, double[]> y) {
		return SymbolDouble.as(andThenNotNullEval(new PearsonsCorrelation()::correlation, y));
	}

	default SymbolDouble<S> spearmansCorrelation(Symbol<S, double[]> y) {
		return SymbolDouble.as(andThenNotNullEval(new SpearmansCorrelation()::correlation, y));
	}

	default SymbolDouble<S> geometricMean() {
		return andThenMathArraysFunction(new GeometricMean());
	}

	default SymbolDouble<S> kurtosis() {
		return andThenMathArraysFunction(new Kurtosis());
	}

	default SymbolDouble<S> mean() {
		return andThenMathArraysFunction(new Mean());
	}

	default SymbolDouble<S> semiVariance() {
		return andThenMathArraysFunction(new SemiVariance());
	}

	default SymbolDouble<S> skewness() {
		return andThenMathArraysFunction(new Skewness());
	}

	default SymbolDouble<S> standardDeviation() {
		return andThenMathArraysFunction(new StandardDeviation());
	}

	default SymbolDouble<S> variance() {
		return andThenMathArraysFunction(new Variance());
	}

	default SymbolDouble<S> max() {
		return andThenMathArraysFunction(new Max());
	}

	default SymbolDouble<S> median() {
		return andThenMathArraysFunction(new Median());
	}

	default SymbolDouble<S> min() {
		return andThenMathArraysFunction(new Min());
	}

	default SymbolDouble<S> percentile(double quantile) {
		return andThenMathArraysFunction(new Percentile());
	}

	default SymbolDouble<S> pSquarePercentile(double p) {
		return andThenMathArraysFunction(new PSquarePercentile(p));
	}

	default SymbolDouble<S> product() {
		return andThenMathArraysFunction(new Product());
	}

	default SymbolDouble<S> sum() {
		return andThenMathArraysFunction(new Sum());
	}

	default SymbolDouble<S> sumOfLogs() {
		return andThenMathArraysFunction(new SumOfLogs());
	}

	default SymbolDouble<S> sumOfSqs() {
		return andThenMathArraysFunction(new SumOfSquares());
	}

}
