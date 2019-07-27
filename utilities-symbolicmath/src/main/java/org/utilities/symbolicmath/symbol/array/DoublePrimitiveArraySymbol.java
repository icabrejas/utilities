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
import org.utilities.symbolicmath.symbol.DoubleSymbol;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.utils.LogicalUtils;
import org.utilities.symbolicmath.utils.array.DoubleArrayUtils;
import org.utilities.symbolicmath.utils.function.ArrayFactory;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;
import org.utilities.symbolicmath.utils.function.FunctionUtils;

public interface DoublePrimitiveArraySymbol<S> extends Symbol<S, double[]> {

	static <S> DoublePrimitiveArraySymbol<S> symbol(Function<S, double[]> func) {
		if (func instanceof DoublePrimitiveArraySymbol) {
			return (DoublePrimitiveArraySymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> DoublePrimitiveArraySymbol<S> constant(double[] constant) {
		return store -> constant;
	}

	default <R> ArraySymbol<S, R> andThenAll(DoubleFunction<R> after, ArrayFactory<R> factory) {
		return andThen(FunctionUtils.notNullEval(v -> DoubleArrayUtils.apply(v, after, factory))).asArray();
	}

	default <R> ArraySymbol<S, R> andThenAll(BiFunction<Double, Double, R> after, Function<S, double[]> y,
			ArrayFactory<R> factory) {
		return andThen(BiFunctionUtils.notNullEval((v, w) -> DoubleArrayUtils.apply(v, after, w, factory)), y)
				.asArray();
	}

	default DoublePrimitiveArraySymbol<S> andThenAll(DoubleUnaryOperator after) {
		return andThen(FunctionUtils.notNullEval(v -> DoubleArrayUtils.apply(v, after))).asDoublePrimitiveArray();
	}

	default DoublePrimitiveArraySymbol<S> andThenAll(DoubleBinaryOperator after, Function<S, double[]> y) {
		return andThen(BiFunctionUtils.notNullEval((v, w) -> DoubleArrayUtils.apply(v, after, w)), y)
				.asDoublePrimitiveArray();
	}

	default BooleanPrimitiveArraySymbol<S> andThenAll(DoublePredicate after) {
		return andThen(FunctionUtils.notNullEval(v -> DoubleArrayUtils.apply(v, after))).asBooleanPrimitiveArray();
	}

	default BooleanPrimitiveArraySymbol<S> andThenAll(BiPredicate<Double, Double> after, Function<S, double[]> y) {
		return andThen(BiFunctionUtils.notNullEval((v, w) -> DoubleArrayUtils.apply(v, after, w)), y)
				.asBooleanPrimitiveArray();
	}

	default DoubleSymbol<S> andThenDistanceMeasure(DistanceMeasure after, Function<S, double[]> y) {
		return andThen(BiFunctionUtils.notNullEval(after::compute), y).asDouble();
	}

	default DoubleSymbol<S> andThenMathArraysFunction(MathArrays.Function after) {
		return andThen(FunctionUtils.notNullEval(after::evaluate)).asDouble();
	}

	default DoublePrimitiveArraySymbol<S> abs() {
		return andThenAll(new Abs()::value);
	}

	default DoublePrimitiveArraySymbol<S> add(Function<S, double[]> y) {
		return andThenAll(new Add()::value, y);
	}

	default DoublePrimitiveArraySymbol<S> ceil() {
		return andThenAll(new Ceil()::value);
	}

	default DoublePrimitiveArraySymbol<S> divide(Function<S, double[]> y) {
		return andThenAll(new Divide()::value, y);
	}

	default DoublePrimitiveArraySymbol<S> exp() {
		return andThenAll(new Exp()::value);
	}

	default DoublePrimitiveArraySymbol<S> expm1() {
		return andThenAll(new Expm1()::value);
	}

	default DoublePrimitiveArraySymbol<S> floor() {
		return andThenAll(new Floor()::value);
	}

	default DoublePrimitiveArraySymbol<S> inverse() {
		return andThenAll(new Inverse()::value);
	}

	default DoublePrimitiveArraySymbol<S> log() {
		return andThenAll(new Log()::value);
	}

	default DoublePrimitiveArraySymbol<S> log10() {
		return andThenAll(new Log10()::value);
	}

	default DoublePrimitiveArraySymbol<S> log1p() {
		return andThenAll(new Log1p()::value);
	}

	default DoublePrimitiveArraySymbol<S> minus() {
		return andThenAll(new Minus()::value);
	}

	default DoublePrimitiveArraySymbol<S> multiply(Function<S, double[]> y) {
		return andThenAll(new Multiply()::value, y);
	}

	default DoublePrimitiveArraySymbol<S> pow(Function<S, double[]> y) {
		return andThenAll(new Pow()::value, y);
	}

	default DoublePrimitiveArraySymbol<S> pow(double p) {
		return andThenAll(new Power(p)::value);
	}

	default DoublePrimitiveArraySymbol<S> root(Function<S, double[]> y) {
		return pow(symbol(y).inverse());
	}

	default DoublePrimitiveArraySymbol<S> signum() {
		return andThenAll(new Signum()::value);
	}

	default DoublePrimitiveArraySymbol<S> sqrt() {
		return andThenAll(new Sqrt()::value);
	}

	default DoublePrimitiveArraySymbol<S> subtract(Function<S, double[]> y) {
		return andThenAll(new Subtract()::value, y);
	}

	default BooleanPrimitiveArraySymbol<S> allEquals(Function<S, double[]> y) {
		return andThenAll(LogicalUtils::coequals, y);
	}

	default BooleanPrimitiveArraySymbol<S> greater(boolean extrict, Function<S, double[]> y) {
		if (extrict) {
			return andThenAll(LogicalUtils::extrictGreater, y);
		} else {
			return andThenAll(LogicalUtils::greater, y);
		}
	}

	default BooleanPrimitiveArraySymbol<S> lower(boolean extrict, Function<S, double[]> y) {
		if (extrict) {
			return andThenAll(LogicalUtils::extrictLower, y);
		} else {
			return andThenAll(LogicalUtils::lower, y);
		}
	}

	default DoublePrimitiveArraySymbol<S> max(Function<S, double[]> y) {
		return andThenAll(new org.apache.commons.math3.analysis.function.Max()::value, y);
	}

	default DoublePrimitiveArraySymbol<S> min(Function<S, double[]> y) {
		return andThenAll(new org.apache.commons.math3.analysis.function.Min()::value, y);
	}

	default BooleanPrimitiveArraySymbol<S> allNotEquals(Function<S, double[]> y) {
		return andThenAll(LogicalUtils::notCoequals, y);
	}

	default BooleanPrimitiveArraySymbol<S> toLogical() {
		return andThenAll(LogicalUtils::parseBoolean);
	}

	default DoublePrimitiveArraySymbol<S> acos() {
		return andThenAll(new Acos()::value);
	}

	default DoublePrimitiveArraySymbol<S> acosh() {
		return andThenAll(new Acosh()::value);
	}

	default DoublePrimitiveArraySymbol<S> asin() {
		return andThenAll(new Asin()::value);
	}

	default DoublePrimitiveArraySymbol<S> asinh() {
		return andThenAll(new Asinh()::value);
	}

	default DoublePrimitiveArraySymbol<S> atan() {
		return andThenAll(new Atan()::value);
	}

	default DoublePrimitiveArraySymbol<S> atan2(Function<S, double[]> y) {
		return andThenAll(new Atan2()::value, y);
	}

	default DoublePrimitiveArraySymbol<S> atanh() {
		return andThenAll(new Atanh()::value);
	}

	default DoublePrimitiveArraySymbol<S> cos() {
		return andThenAll(new Cos()::value);
	}

	default DoublePrimitiveArraySymbol<S> cosh() {
		return andThenAll(new Cosh()::value);
	}

	default DoublePrimitiveArraySymbol<S> sin() {
		return andThenAll(new Sin()::value);
	}

	default DoublePrimitiveArraySymbol<S> sinh() {
		return andThenAll(new Sinh()::value);
	}

	default DoublePrimitiveArraySymbol<S> tan() {
		return andThenAll(new Tan()::value);
	}

	default DoublePrimitiveArraySymbol<S> tanh() {
		return andThenAll(new Tanh()::value);
	}

	default DoublePrimitiveArraySymbol<S> toDegrees() {
		return andThenAll(Math::toDegrees);
	}

	default DoublePrimitiveArraySymbol<S> toRadians() {
		return andThenAll(Math::toRadians);
	}

	default DoubleSymbol<S> canberraDistance(Function<S, double[]> y) {
		return andThenDistanceMeasure(new CanberraDistance(), y);
	}

	default DoubleSymbol<S> chebyshevDistance(Function<S, double[]> y) {
		return andThenDistanceMeasure(new ChebyshevDistance(), y);
	}

	default DoubleSymbol<S> earthMoversDistance(Function<S, double[]> y) {
		return andThenDistanceMeasure(new EarthMoversDistance(), y);
	}

	default DoubleSymbol<S> euclideanDistance(Function<S, double[]> y) {
		return andThenDistanceMeasure(new EuclideanDistance(), y);
	}

	default DoubleSymbol<S> manhattanDistance(Function<S, double[]> y) {
		return andThenDistanceMeasure(new ManhattanDistance(), y);
	}

	default DoubleSymbol<S> covariance(Symbol<S, double[]> y) {
		return andThen(BiFunctionUtils.notNullEval(new Covariance()::covariance), y).asDouble();
	}

	default DoubleSymbol<S> covariance(Symbol<S, double[]> y, boolean biasCorrected) {
		return andThen(BiFunctionUtils.notNullEval((a, b) -> new Covariance().covariance(a, b, biasCorrected)), y)
				.asDouble();
	}

	default DoubleSymbol<S> storelessCovariance(Symbol<S, double[]> y, int dim) {
		return andThen(BiFunctionUtils.notNullEval(new StorelessCovariance(dim)::covariance), y).asDouble();
	}

	default DoubleSymbol<S> storelessCovariance(Symbol<S, double[]> y, int dim, boolean biasCorrected) {
		return andThen(BiFunctionUtils.notNullEval(new StorelessCovariance(dim, biasCorrected)::covariance), y)
				.asDouble();
	}

	default DoubleSymbol<S> kendallsCorrelation(Symbol<S, double[]> y) {
		return andThen(BiFunctionUtils.notNullEval(new KendallsCorrelation()::correlation), y).asDouble();
	}

	default DoubleSymbol<S> pearsonsCorrelation(Symbol<S, double[]> y) {
		return andThen(BiFunctionUtils.notNullEval(new PearsonsCorrelation()::correlation), y).asDouble();
	}

	default DoubleSymbol<S> spearmansCorrelation(Symbol<S, double[]> y) {
		return andThen(BiFunctionUtils.notNullEval(new SpearmansCorrelation()::correlation), y).asDouble();
	}

	default DoubleSymbol<S> geometricMean() {
		return andThenMathArraysFunction(new GeometricMean());
	}

	default DoubleSymbol<S> kurtosis() {
		return andThenMathArraysFunction(new Kurtosis());
	}

	default DoubleSymbol<S> mean() {
		return andThenMathArraysFunction(new Mean());
	}

	default DoubleSymbol<S> semiVariance() {
		return andThenMathArraysFunction(new SemiVariance());
	}

	default DoubleSymbol<S> skewness() {
		return andThenMathArraysFunction(new Skewness());
	}

	default DoubleSymbol<S> standardDeviation() {
		return andThenMathArraysFunction(new StandardDeviation());
	}

	default DoubleSymbol<S> variance() {
		return andThenMathArraysFunction(new Variance());
	}

	default DoubleSymbol<S> max() {
		return andThenMathArraysFunction(new Max());
	}

	default DoubleSymbol<S> median() {
		return andThenMathArraysFunction(new Median());
	}

	default DoubleSymbol<S> min() {
		return andThenMathArraysFunction(new Min());
	}

	default DoubleSymbol<S> percentile(double quantile) {
		return andThenMathArraysFunction(new Percentile());
	}

	default DoubleSymbol<S> pSquarePercentile(double p) {
		return andThenMathArraysFunction(new PSquarePercentile(p));
	}

	default DoubleSymbol<S> product() {
		return andThenMathArraysFunction(new Product());
	}

	default DoubleSymbol<S> sum() {
		return andThenMathArraysFunction(new Sum());
	}

	default DoubleSymbol<S> sumOfLogs() {
		return andThenMathArraysFunction(new SumOfLogs());
	}

	default DoubleSymbol<S> sumOfSqs() {
		return andThenMathArraysFunction(new SumOfSquares());
	}

}
