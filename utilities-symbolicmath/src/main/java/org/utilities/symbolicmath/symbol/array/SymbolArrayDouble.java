package org.utilities.symbolicmath.symbol.array;

import java.util.function.DoubleFunction;
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
import org.utilities.core.util.function.ArrayFactory;
import org.utilities.core.util.function.DoubleBiFunctionPlus;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.utils.LogicalUtils;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;

public interface SymbolArrayDouble<S> extends SymbolArray<S, Double> {

	static <S> SymbolArrayDouble<S> empty() {
		return constant(null);
	}

	static <S> SymbolArrayDouble<S> constant(Double[] constant) {
		return s -> constant;
	}

	static <S> SymbolArrayDouble<S> as(Function<S, Double[]> func) {
		if (func instanceof SymbolArrayDouble) {
			return (SymbolArrayDouble<S>) func;
		} else {
			return func::apply;
		}
	}

	default <R> SymbolArray<S, R> andThenForeachNotNullEval(DoubleFunction<R> after, ArrayFactory<R> factory) {
		return andThenForeachNotNullEval(after::apply, factory);
	}

	default <R> SymbolArray<S, R> andThenForeachNotNullEval(DoubleBiFunctionPlus<R> after, Symbol<S, Double[]> y,
			ArrayFactory<R> factory) {
		return andThenForeachNotNullEval(after::apply, y, factory);
	}

	default SymbolArrayDouble<S> andThenForeachNotNullEval(DoubleFunction<Double> after) {
		return as(andThenForeachNotNullEval(after::apply, Double[]::new));
	}

	default SymbolArrayDouble<S> andThenForeachNotNullEval(DoubleBiFunctionPlus<Double> after, Symbol<S, Double[]> y) {
		return as(andThenForeachNotNullEval(after::apply, y, Double[]::new));
	}

	default SymbolArrayDouble<S> abs() {
		return andThenForeachNotNullEval(new Abs()::value);
	}

	default SymbolArrayDouble<S> add(Symbol<S, Double[]> y) {
		return andThenForeachNotNullEval(new Add()::value, y);
	}

	default SymbolArrayDouble<S> ceil() {
		return andThenForeachNotNullEval(new Ceil()::value);
	}

	default SymbolArrayDouble<S> divide(Symbol<S, Double[]> y) {
		return andThenForeachNotNullEval(new Divide()::value, y);
	}

	default SymbolArrayDouble<S> exp() {
		return andThenForeachNotNullEval(new Exp()::value);
	}

	default SymbolArrayDouble<S> expm1() {
		return andThenForeachNotNullEval(new Expm1()::value);
	}

	default SymbolArrayDouble<S> floor() {
		return andThenForeachNotNullEval(new Floor()::value);
	}

	default SymbolArrayDouble<S> inverse() {
		return andThenForeachNotNullEval(new Inverse()::value);
	}

	default SymbolArrayDouble<S> log() {
		return andThenForeachNotNullEval(new Log()::value);
	}

	default SymbolArrayDouble<S> log10() {
		return andThenForeachNotNullEval(new Log10()::value);
	}

	default SymbolArrayDouble<S> log1p() {
		return andThenForeachNotNullEval(new Log1p()::value);
	}

	default SymbolArrayDouble<S> minus() {
		return andThenForeachNotNullEval(new Minus()::value);
	}

	default SymbolArrayDouble<S> multiply(Symbol<S, Double[]> y) {
		return andThenForeachNotNullEval(new Multiply()::value, y);
	}

	default SymbolArrayDouble<S> pow(Symbol<S, Double[]> y) {
		return andThenForeachNotNullEval(new Pow()::value, y);
	}

	default SymbolArrayDouble<S> pow(double p) {
		return andThenForeachNotNullEval(new Power(p)::value);
	}

	default SymbolArrayDouble<S> root(Symbol<S, Double[]> y) {
		return pow(as(y).inverse());
	}

	default SymbolArrayDouble<S> signum() {
		return andThenForeachNotNullEval(new Signum()::value);
	}

	default SymbolArrayDouble<S> sqrt() {
		return andThenForeachNotNullEval(new Sqrt()::value);
	}

	default SymbolArrayDouble<S> subtract(Symbol<S, Double[]> y) {
		return andThenForeachNotNullEval(new Subtract()::value, y);
	}

	default SymbolArrayBoolean<S> allEquals(Symbol<S, Double[]> y) {
		return SymbolArrayBoolean.as(andThenForeachNotNullEval(LogicalUtils::equals, y, Boolean[]::new));
	}

	default SymbolArrayBoolean<S> greater(boolean extrict, Symbol<S, Double[]> y) {
		return SymbolArrayBoolean.as(andThenForeachNotNullEval(
				extrict ? LogicalUtils::greater : LogicalUtils::greaterOrEquals, y, Boolean[]::new));
	}

	default SymbolArrayBoolean<S> lower(boolean extrict, Symbol<S, Double[]> y) {
		return SymbolArrayBoolean.as(andThenForeachNotNullEval(
				extrict ? LogicalUtils::lower : LogicalUtils::lowerOrEquals, y, Boolean[]::new));
	}

	default SymbolArrayDouble<S> max(Symbol<S, Double[]> y) {
		return andThenForeachNotNullEval(new org.apache.commons.math3.analysis.function.Max()::value, y);
	}

	default SymbolArrayDouble<S> min(Symbol<S, Double[]> y) {
		return andThenForeachNotNullEval(new org.apache.commons.math3.analysis.function.Min()::value, y);
	}

	default SymbolArrayBoolean<S> allNotEquals(Symbol<S, Double[]> y) {
		return SymbolArrayBoolean.as(andThenForeachNotNullEval(LogicalUtils::notEquals, y, Boolean[]::new));
	}

	default SymbolArrayBoolean<S> toLogical() {
		return SymbolArrayBoolean.as(andThenForeachNotNullEval(LogicalUtils::parseBoolean, Boolean[]::new));
	}

	default SymbolArrayDouble<S> acos() {
		return andThenForeachNotNullEval(new Acos()::value);
	}

	default SymbolArrayDouble<S> acosh() {
		return andThenForeachNotNullEval(new Acosh()::value);
	}

	default SymbolArrayDouble<S> asin() {
		return andThenForeachNotNullEval(new Asin()::value);
	}

	default SymbolArrayDouble<S> asinh() {
		return andThenForeachNotNullEval(new Asinh()::value);
	}

	default SymbolArrayDouble<S> atan() {
		return andThenForeachNotNullEval(new Atan()::value);
	}

	default SymbolArrayDouble<S> atan2(Symbol<S, Double[]> y) {
		return andThenForeachNotNullEval(new Atan2()::value, y);
	}

	default SymbolArrayDouble<S> atanh() {
		return andThenForeachNotNullEval(new Atanh()::value);
	}

	default SymbolArrayDouble<S> cos() {
		return andThenForeachNotNullEval(new Cos()::value);
	}

	default SymbolArrayDouble<S> cosh() {
		return andThenForeachNotNullEval(new Cosh()::value);
	}

	default SymbolArrayDouble<S> sin() {
		return andThenForeachNotNullEval(new Sin()::value);
	}

	default SymbolArrayDouble<S> sinh() {
		return andThenForeachNotNullEval(new Sinh()::value);
	}

	default SymbolArrayDouble<S> tan() {
		return andThenForeachNotNullEval(new Tan()::value);
	}

	default SymbolArrayDouble<S> tanh() {
		return andThenForeachNotNullEval(new Tanh()::value);
	}

	default SymbolArrayDouble<S> toDegrees() {
		return andThenForeachNotNullEval(Math::toDegrees);
	}

	default SymbolArrayDouble<S> toRadians() {
		return andThenForeachNotNullEval(Math::toRadians);
	}

	default SymbolArrayDouble<S> nullOmit() {
		return as(filter(isNotNullForeach(), Double[]::new));
	}

	default SymbolArrayDoublePrimitive<S> toPrimitive(boolean nullOmit) {
		if (nullOmit) {
			return this.nullOmit()
					.andThenForeach(Double::doubleValue);
		} else {
			return this.containsNull()
					.ifElse(SymbolArrayDoublePrimitive.constant(null), andThenForeach(Double::doubleValue))
					.apply(SymbolArrayDoublePrimitive::as);
		}
	}

}
