package org.utilities.symbolicmath.symbol;

import static org.apache.commons.math3.analysis.polynomials.PolynomialsUtils.createJacobiPolynomial;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.math3.analysis.BivariateFunction;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.UnivariateMatrixFunction;
import org.apache.commons.math3.analysis.UnivariateVectorFunction;
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
import org.apache.commons.math3.analysis.interpolation.HermiteInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;
import org.apache.commons.math3.analysis.polynomials.PolynomialsUtils;
import org.apache.commons.math3.special.BesselJ;
import org.utilities.symbolicmath.symbol.array.SymbolArray2DoublePrimitive;
import org.utilities.symbolicmath.symbol.array.SymbolArrayDoublePrimitive;
import org.utilities.symbolicmath.utils.LogicalUtils;
import org.utilities.symbolicmath.utils.StatisticsUtils;

public interface SymbolDouble<S> extends Symbol<S, Double> {

	static <S> SymbolDouble<S> empty() {
		return constant(null);
	}

	static <S> SymbolDouble<S> constant(Double constant) {
		return s -> constant;
	}

	static <S> SymbolDouble<S> as(Function<S, Double> func) {
		if (func instanceof SymbolDouble) {
			return (SymbolDouble<S>) func;
		} else {
			return func::apply;
		}
	}

	default SymbolDouble<S> toDegrees() {
		return as(andThenNotNullEval((Function<Double, Double>) Math::toDegrees));
	}

	default SymbolDouble<S> toRadians() {
		return as(andThenNotNullEval((Function<Double, Double>) Math::toRadians));
	}

	default SymbolBoolean<S> toLogical() {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::parseBoolean));
	}

	default SymbolBoolean<S> greater(Symbol<S, Double> y) {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::greater, y));
	}

	default SymbolBoolean<S> greaterOrEquals(Symbol<S, Double> y) {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::greaterOrEquals, y));
	}

	default SymbolBoolean<S> lower(Symbol<S, Double> y) {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::lower, y));
	}

	default SymbolBoolean<S> lowerOrEquals(Symbol<S, Double> y) {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::lowerOrEquals, y));
	}

	default SymbolBoolean<S> equals(Symbol<S, Double> y) {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::equals, y));
	}

	default SymbolBoolean<S> notEquals(Symbol<S, Double> y) {
		return SymbolBoolean.as(andThenNotNullEval(LogicalUtils::notEquals, y));
	}

	default <R> SymbolDouble<S> andThenNotNullEval(UnivariateFunction func) {
		return as(andThenNotNullEval((Function<Double, Double>) func::value));
	}

	default SymbolDouble<S> abs() {
		return andThenNotNullEval(new Abs());
	}

	default SymbolDouble<S> ceil() {
		return andThenNotNullEval(new Ceil());
	}

	default SymbolDouble<S> exp() {
		return andThenNotNullEval(new Exp());
	}

	default SymbolDouble<S> expm1() {
		return andThenNotNullEval(new Expm1());
	}

	default SymbolDouble<S> floor() {
		return andThenNotNullEval(new Floor());
	}

	default SymbolDouble<S> inverse() {
		return andThenNotNullEval(new Inverse());
	}

	default SymbolDouble<S> log() {
		return andThenNotNullEval(new Log());
	}

	default SymbolDouble<S> log10() {
		return andThenNotNullEval(new Log10());
	}

	default SymbolDouble<S> log1p() {
		return andThenNotNullEval(new Log1p());
	}

	default SymbolDouble<S> minus() {
		return andThenNotNullEval(new Minus());
	}

	default SymbolDouble<S> signum() {
		return andThenNotNullEval(new Signum());
	}

	default SymbolDouble<S> sqrt() {
		return andThenNotNullEval(new Sqrt());
	}

	default SymbolDouble<S> gaussian() {
		return andThenNotNullEval(new Gaussian());
	}

	default SymbolDouble<S> sigmoid() {
		return andThenNotNullEval(new Sigmoid());
	}

	default SymbolDouble<S> logit() {
		return andThenNotNullEval(new Logit());
	}

	default SymbolDouble<S> rint() {
		return andThenNotNullEval(new Rint());
	}

	default SymbolDouble<S> ulp() {
		return andThenNotNullEval(new Ulp());
	}

	default SymbolDouble<S> cbrt() {
		return andThenNotNullEval(new Cbrt());
	}

	default SymbolDouble<S> acos() {
		return andThenNotNullEval(new Acos());
	}

	default SymbolDouble<S> acosh() {
		return andThenNotNullEval(new Acosh());
	}

	default SymbolDouble<S> asin() {
		return andThenNotNullEval(new Asin());
	}

	default SymbolDouble<S> asinh() {
		return andThenNotNullEval(new Asinh());
	}

	default SymbolDouble<S> atan() {
		return andThenNotNullEval(new Atan());
	}

	default SymbolDouble<S> atanh() {
		return andThenNotNullEval(new Atanh());
	}

	default SymbolDouble<S> cos() {
		return andThenNotNullEval(new Cos());
	}

	default SymbolDouble<S> cosh() {
		return andThenNotNullEval(new Cosh());
	}

	default SymbolDouble<S> sin() {
		return andThenNotNullEval(new Sin());
	}

	default SymbolDouble<S> sinh() {
		return andThenNotNullEval(new Sinh());
	}

	default SymbolDouble<S> tan() {
		return andThenNotNullEval(new Tan());
	}

	default SymbolDouble<S> tanh() {
		return andThenNotNullEval(new Tanh());
	}

	default <R> SymbolDouble<S> andThenNotNullEval(BivariateFunction func, Symbol<S, Double> y) {
		return as(andThenNotNullEval((BiFunction<Double, Double, Double>) func::value, y));
	}

	default SymbolDouble<S> add(Symbol<S, Double> y) {
		return andThenNotNullEval(new Add(), y);
	}

	default SymbolDouble<S> divide(Symbol<S, Double> y) {
		return andThenNotNullEval(new Divide(), y);
	}

	default SymbolDouble<S> multiply(Symbol<S, Double> y) {
		return andThenNotNullEval(new Multiply(), y);
	}

	default SymbolDouble<S> pow(Symbol<S, Double> y) {
		return andThenNotNullEval(new Pow(), y);
	}

	default SymbolDouble<S> root(Symbol<S, Double> y) {
		return pow(as(y).inverse());
	}

	default SymbolDouble<S> subtract(Symbol<S, Double> y) {
		return andThenNotNullEval(new Subtract(), y);
	}

	default SymbolDouble<S> max(Symbol<S, Double> y) {
		return andThenNotNullEval(new Max(), y);
	}

	default SymbolDouble<S> min(Symbol<S, Double> y) {
		return andThenNotNullEval(new Min(), y);
	}

	default SymbolDouble<S> atan2(Symbol<S, Double> y) {
		return andThenNotNullEval(new Atan2(), y);
	}

	default SymbolDouble<S> andThenNotNullEval(Symbol<S, UnivariateFunction> func) {
		return as(func.andThenNotNullEval(UnivariateFunction::value, this));
	}

	default <P> SymbolDouble<S> andThenNotNullEval(Symbol<S, P> param, Function<P, UnivariateFunction> func) {
		return andThenNotNullEval(param.andThenNotNullEval(func));
	}

	default SymbolDouble<S> besselJ(Symbol<S, Double> order) {
		return andThenNotNullEval(order, BesselJ::new);
	}

	default SymbolDouble<S> polynomial(Symbol<S, double[]> c) {
		return andThenNotNullEval(c, PolynomialFunction::new);
	}

	default SymbolDouble<S> polynomialChebyshevPolynomial(Symbol<S, Integer> degree) {
		return andThenNotNullEval(degree, PolynomialsUtils::createChebyshevPolynomial);
	}

	default SymbolDouble<S> polynomialHermitePolynomial(Symbol<S, Integer> degree) {
		return andThenNotNullEval(degree, PolynomialsUtils::createHermitePolynomial);
	}

	default SymbolDouble<S> polynomialLaguerrePolynomial(Symbol<S, Integer> degree) {
		return andThenNotNullEval(degree, PolynomialsUtils::createLaguerrePolynomial);
	}

	default SymbolDouble<S> polynomialLegendrePolynomial(Symbol<S, Integer> degree) {
		return andThenNotNullEval(degree, PolynomialsUtils::createLegendrePolynomial);
	}

	default <P1, P2> SymbolDouble<S> andThenNotNullEval(Symbol<S, P1> param1, Symbol<S, P2> param2,
			BiFunction<P1, P2, UnivariateFunction> func) {
		return andThenNotNullEval(param1.andThenNotNullEval(func, param2));
	}

	default SymbolDouble<S> gaussian(Symbol<S, Double> mean, Symbol<S, Double> sigma) {
		return andThenNotNullEval(mean, sigma, Gaussian::new);
	}

	default SymbolDouble<S> sigmoid(Symbol<S, Double> lo, Symbol<S, Double> hi) {
		return andThenNotNullEval(lo, hi, Sigmoid::new);
	}

	default SymbolDouble<S> logit(Symbol<S, Double> lo, Symbol<S, Double> hi) {
		return andThenNotNullEval(lo, hi, Logit::new);
	}

	default SymbolDouble<S> stepFunction(Symbol<S, double[]> x, Symbol<S, double[]> y) {
		return andThenNotNullEval(x, y, StepFunction::new);
	}

	default SymbolDouble<S> polynomialLagrangeForm(Symbol<S, double[]> x, Symbol<S, double[]> y) {
		return andThenNotNullEval(x, y, PolynomialFunctionLagrangeForm::new);
	}

	default SymbolDouble<S> polynomialNewtonForm(Symbol<S, double[]> a, Symbol<S, double[]> c) {
		return andThenNotNullEval(a, c, PolynomialFunctionNewtonForm::new);
	}

	default SymbolDouble<S> linearInterpolation(Symbol<S, double[]> x, Symbol<S, double[]> y) {
		return andThenNotNullEval(x, y, StatisticsUtils::linearInterpolator);
	}

	// FIXME check null cases
	default SymbolDouble<S> harmonicOscillator(Symbol<S, Double> amplitude, Symbol<S, Double> omega,
			Symbol<S, Double> phase) {
		return s -> new HarmonicOscillator(amplitude.apply(s), omega.apply(s), phase.apply(s)).value(this.apply(s));
	}

	// FIXME check null cases
	default SymbolDouble<S> polynomialJacobiPolynomial(Symbol<S, Integer> arg0, Symbol<S, Integer> arg1,
			Symbol<S, Integer> arg2) {
		return s -> createJacobiPolynomial(arg0.apply(s), arg1.apply(s), arg2.apply(s)).value(this.apply(s));
	}

	// FIXME check null cases
	default SymbolDouble<S> gaussian(Symbol<S, Double> norm, Symbol<S, Double> mean, Symbol<S, Double> sigma) {
		return s -> new Gaussian(norm.apply(s), mean.apply(s), sigma.apply(s)).value(this.apply(s));
	}

	// FIXME check null cases
	default SymbolDouble<S> logistic(Symbol<S, Double> k, Symbol<S, Double> m, Symbol<S, Double> b, Symbol<S, Double> q,
			Symbol<S, Double> a, Symbol<S, Double> n) {
		return s -> new Logistic(k.apply(s), m.apply(s), b.apply(s), q.apply(s), a.apply(s), n.apply(s))
				.value(this.apply(s));
	}

	default <R> SymbolArrayDoublePrimitive<S> andThenVectorFunc(UnivariateVectorFunction func) {
		return SymbolArrayDoublePrimitive.as(andThenNotNullEval(func::value));
	}

	default <R> SymbolArrayDoublePrimitive<S> hermiteInterpolation() {
		return andThenVectorFunc(new HermiteInterpolator());
	}

	// TODO
	default <R> SymbolArray2DoublePrimitive<S> andThenMatrixFunc(UnivariateMatrixFunction func) {
		return SymbolArray2DoublePrimitive.as(andThenNotNullEval(func::value));
	}

}
