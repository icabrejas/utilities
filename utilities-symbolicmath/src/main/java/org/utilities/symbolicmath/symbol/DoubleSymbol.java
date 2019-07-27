package org.utilities.symbolicmath.symbol;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
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
import org.apache.commons.math3.analysis.function.Cbrt;
import org.apache.commons.math3.analysis.function.Ceil;
import org.apache.commons.math3.analysis.function.Cos;
import org.apache.commons.math3.analysis.function.Cosh;
import org.apache.commons.math3.analysis.function.Divide;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.commons.math3.analysis.function.Expm1;
import org.apache.commons.math3.analysis.function.Floor;
import org.apache.commons.math3.analysis.function.Gaussian;
import org.apache.commons.math3.analysis.function.HarmonicOscillator;
import org.apache.commons.math3.analysis.function.Inverse;
import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.analysis.function.Log10;
import org.apache.commons.math3.analysis.function.Log1p;
import org.apache.commons.math3.analysis.function.Logistic;
import org.apache.commons.math3.analysis.function.Logit;
import org.apache.commons.math3.analysis.function.Max;
import org.apache.commons.math3.analysis.function.Min;
import org.apache.commons.math3.analysis.function.Minus;
import org.apache.commons.math3.analysis.function.Multiply;
import org.apache.commons.math3.analysis.function.Pow;
import org.apache.commons.math3.analysis.function.Power;
import org.apache.commons.math3.analysis.function.Rint;
import org.apache.commons.math3.analysis.function.Sigmoid;
import org.apache.commons.math3.analysis.function.Signum;
import org.apache.commons.math3.analysis.function.Sin;
import org.apache.commons.math3.analysis.function.Sinh;
import org.apache.commons.math3.analysis.function.Sqrt;
import org.apache.commons.math3.analysis.function.StepFunction;
import org.apache.commons.math3.analysis.function.Subtract;
import org.apache.commons.math3.analysis.function.Tan;
import org.apache.commons.math3.analysis.function.Tanh;
import org.apache.commons.math3.analysis.function.Ulp;
import org.utilities.symbolicmath.utils.LogicalUtils;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;
import org.utilities.symbolicmath.utils.function.DoubleBiFunction;
import org.utilities.symbolicmath.utils.function.FunctionUtils;

public interface DoubleSymbol<S> extends Symbol<S, Double> {

	static <S> DoubleSymbol<S> constant(Double constant) {
		return store -> constant;
	}

	static <S> DoubleSymbol<S> symbol(Function<S, Double> func) {
		if (func instanceof DoubleSymbol) {
			return (DoubleSymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	default <R> Symbol<S, R> andThenNotNullEval(DoubleFunction<R> after) {
		return andThen(FunctionUtils.notNullEval(after::apply));
	}

	default DoubleSymbol<S> andThenNotNullEval(DoubleUnaryOperator after) {
		return andThen(FunctionUtils.notNullEval(after::applyAsDouble)).asDouble();
	}

	default <R> Symbol<S, R> andThenDouble(DoubleBiFunction<R> after, Function<S, Double> y) {
		return andThen(BiFunctionUtils.notNullEval(after::apply), y);
	}

	default <R> DoubleSymbol<S> andThenDouble(DoubleBinaryOperator after, Function<S, Double> y) {
		return andThen(BiFunctionUtils.notNullEval(after::applyAsDouble), y).asDouble();
	}

	default DoubleSymbol<S> abs() {
		return andThenNotNullEval(new Abs()::value);
	}

	default DoubleSymbol<S> add(Function<S, Double> y) {
		return andThenDouble(new Add()::value, y);
	}

	default DoubleSymbol<S> ceil() {
		return andThenNotNullEval(new Ceil()::value);
	}

	default DoubleSymbol<S> divide(Function<S, Double> y) {
		return andThenDouble(new Divide()::value, y);
	}

	default DoubleSymbol<S> exp() {
		return andThenNotNullEval(new Exp()::value);
	}

	default DoubleSymbol<S> expm1() {
		return andThenNotNullEval(new Expm1()::value);
	}

	default DoubleSymbol<S> floor() {
		return andThenNotNullEval(new Floor()::value);
	}

	default DoubleSymbol<S> inverse() {
		return andThenNotNullEval(new Inverse()::value);
	}

	default DoubleSymbol<S> log() {
		return andThenNotNullEval(new Log()::value);
	}

	default DoubleSymbol<S> log10() {
		return andThenNotNullEval(new Log10()::value);
	}

	default DoubleSymbol<S> log1p() {
		return andThenNotNullEval(new Log1p()::value);
	}

	default DoubleSymbol<S> minus() {
		return andThenNotNullEval(new Minus()::value);
	}

	default DoubleSymbol<S> multiply(Function<S, Double> y) {
		return andThenDouble(new Multiply()::value, y);
	}

	default DoubleSymbol<S> pow(Function<S, Double> y) {
		return andThenDouble(new Pow()::value, y);
	}

	default DoubleSymbol<S> pow(double p) {
		return andThenNotNullEval(new Power(p)::value);
	}

	default DoubleSymbol<S> root(Function<S, Double> y) {
		return pow(symbol(y).inverse()).asDouble();
	}

	default DoubleSymbol<S> signum() {
		return andThenNotNullEval(new Signum()::value);
	}

	default DoubleSymbol<S> sqrt() {
		return andThenNotNullEval(new Sqrt()::value);
	}

	default DoubleSymbol<S> subtract(Function<S, Double> y) {
		return andThenDouble(new Subtract()::value, y);
	}

	default BooleanSymbol<S> equals(Function<S, Double> y) {
		return andThenDouble(LogicalUtils::coequals, y).asBoolean();
	}

	default BooleanSymbol<S> greater(boolean extrict, Function<S, Double> y) {
		if (extrict) {
			return andThenDouble(LogicalUtils::extrictGreater, y).asBoolean();
		} else {
			return andThenDouble(LogicalUtils::greater, y).asBoolean();
		}
	}

	default BooleanSymbol<S> lower(boolean extrict, Function<S, Double> y) {
		if (extrict) {
			return andThenDouble(LogicalUtils::extrictLower, y).asBoolean();
		} else {
			return andThenDouble(LogicalUtils::lower, y).asBoolean();
		}
	}

	default DoubleSymbol<S> max(Function<S, Double> y) {
		return andThenDouble(new Max()::value, y);
	}

	default DoubleSymbol<S> min(Function<S, Double> y) {
		return andThenDouble(new Min()::value, y);
	}

	default BooleanSymbol<S> notEquals(Function<S, Double> y) {
		return andThenDouble(LogicalUtils::notCoequals, y).asBoolean();
	}

	default BooleanSymbol<S> toLogical() {
		return andThenNotNullEval(LogicalUtils::parseBoolean).asBoolean();
	}

	default DoubleSymbol<S> acos() {
		return andThenNotNullEval(new Acos()::value);
	}

	default DoubleSymbol<S> acosh() {
		return andThenNotNullEval(new Acosh()::value);
	}

	default DoubleSymbol<S> asin() {
		return andThenNotNullEval(new Asin()::value);
	}

	default DoubleSymbol<S> asinh() {
		return andThenNotNullEval(new Asinh()::value);
	}

	default DoubleSymbol<S> atan() {
		return andThenNotNullEval(new Atan()::value);
	}

	default DoubleSymbol<S> atan2(Function<S, Double> y) {
		return andThenDouble(new Atan2()::value, y);
	}

	default DoubleSymbol<S> atanh() {
		return andThenNotNullEval(new Atanh()::value);
	}

	default DoubleSymbol<S> cos() {
		return andThenNotNullEval(new Cos()::value);
	}

	default DoubleSymbol<S> cosh() {
		return andThenNotNullEval(new Cosh()::value);
	}

	default DoubleSymbol<S> sin() {
		return andThenNotNullEval(new Sin()::value);
	}

	default DoubleSymbol<S> sinh() {
		return andThenNotNullEval(new Sinh()::value);
	}

	default DoubleSymbol<S> tan() {
		return andThenNotNullEval(new Tan()::value);
	}

	default DoubleSymbol<S> tanh() {
		return andThenNotNullEval(new Tanh()::value);
	}

	default DoubleSymbol<S> toDegrees() {
		return andThenNotNullEval(Math::toDegrees);
	}

	default DoubleSymbol<S> toRadians() {
		return andThenNotNullEval(Math::toRadians);
	}

	default DoubleSymbol<S> gaussian() {
		return andThenNotNullEval(new Gaussian()::value);
	}

	default DoubleSymbol<S> gaussian(double mean, double sigma) {
		return andThenNotNullEval(new Gaussian(mean, sigma)::value);
	}

	default DoubleSymbol<S> gaussian(double norm, double mean, double sigma) {
		return andThenNotNullEval(new Gaussian(norm, mean, sigma)::value);
	}

	default DoubleSymbol<S> logistic(double k, double m, double b, double q, double a, double n) {
		return andThenNotNullEval(new Logistic(k, m, b, q, a, n)::value);
	}

	default DoubleSymbol<S> logit() {
		return andThenNotNullEval(new Logit()::value);
	}

	default DoubleSymbol<S> logit(double lo, double hi) {
		return andThenNotNullEval(new Logit(lo, hi)::value);
	}

	default DoubleSymbol<S> sigmoid() {
		return andThenNotNullEval(new Sigmoid()::value);
	}

	default DoubleSymbol<S> sigmoid(double lo, double hi) {
		return andThenNotNullEval(new Sigmoid(lo, hi)::value);
	}

	default DoubleSymbol<S> rint() {
		return andThenNotNullEval(new Rint()::value);
	}

	default DoubleSymbol<S> ulp() {
		return andThenNotNullEval(new Ulp()::value);
	}

	default DoubleSymbol<S> harmonicOscillator(double amplitude, double omega, double phase) {
		return andThenNotNullEval(new HarmonicOscillator(amplitude, omega, phase)::value);
	}

	default DoubleSymbol<S> cbrt() {
		return andThenNotNullEval(new Cbrt()::value);
	}

	default DoubleSymbol<S> stepFunction(double[] x, double[] y) {
		return andThenNotNullEval(new StepFunction(x, y)::value);
	}

}
