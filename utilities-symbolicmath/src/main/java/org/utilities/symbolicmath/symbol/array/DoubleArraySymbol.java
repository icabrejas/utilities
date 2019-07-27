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
import org.utilities.symbolicmath.utils.LogicalUtils;
import org.utilities.symbolicmath.utils.function.ArrayFactory;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;
import org.utilities.symbolicmath.utils.function.DoubleBiFunction;
import org.utilities.symbolicmath.utils.function.FunctionUtils;

public interface DoubleArraySymbol<S> extends ArraySymbol<S, Double> {

	static <S> DoubleArraySymbol<S> symbol(Function<S, Double[]> func) {
		if (func instanceof DoubleArraySymbol) {
			return (DoubleArraySymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> DoubleArraySymbol<S> constant(Double[] constant) {
		return store -> constant;
	}

	default <R> ArraySymbol<S, R> andThenAllNotNullEval(DoubleFunction<R> after, ArrayFactory<R> factory) {
		return andThenAll(FunctionUtils.notNullEval(after::apply), factory);
	}

	default <R> ArraySymbol<S, R> andThenAllNotNullEval(DoubleBiFunction<R> after, Function<S, Double[]> y,
			ArrayFactory<R> factory) {
		return andThenAll(BiFunctionUtils.notNullEval(after::apply), y, factory);
	}

	default DoubleArraySymbol<S> andThenAllNotNullEval(DoubleFunction<Double> after) {
		return andThenAll(FunctionUtils.notNullEval(after::apply), Double[]::new).asDoubleArray();
	}

	default DoubleArraySymbol<S> andThenAllNotNullEval(DoubleBiFunction<Double> after, Function<S, Double[]> y) {
		return andThenAll(BiFunctionUtils.notNullEval(after::apply), y, Double[]::new).asDoubleArray();
	}

	default DoubleArraySymbol<S> abs() {
		return andThenAllNotNullEval(new Abs()::value);
	}

	default DoubleArraySymbol<S> add(Function<S, Double[]> y) {
		return andThenAllNotNullEval(new Add()::value, y);
	}

	default DoubleArraySymbol<S> ceil() {
		return andThenAllNotNullEval(new Ceil()::value);
	}

	default DoubleArraySymbol<S> divide(Function<S, Double[]> y) {
		return andThenAllNotNullEval(new Divide()::value, y);
	}

	default DoubleArraySymbol<S> exp() {
		return andThenAllNotNullEval(new Exp()::value);
	}

	default DoubleArraySymbol<S> expm1() {
		return andThenAllNotNullEval(new Expm1()::value);
	}

	default DoubleArraySymbol<S> floor() {
		return andThenAllNotNullEval(new Floor()::value);
	}

	default DoubleArraySymbol<S> inverse() {
		return andThenAllNotNullEval(new Inverse()::value);
	}

	default DoubleArraySymbol<S> log() {
		return andThenAllNotNullEval(new Log()::value);
	}

	default DoubleArraySymbol<S> log10() {
		return andThenAllNotNullEval(new Log10()::value);
	}

	default DoubleArraySymbol<S> log1p() {
		return andThenAllNotNullEval(new Log1p()::value);
	}

	default DoubleArraySymbol<S> minus() {
		return andThenAllNotNullEval(new Minus()::value);
	}

	default DoubleArraySymbol<S> multiply(Function<S, Double[]> y) {
		return andThenAllNotNullEval(new Multiply()::value, y);
	}

	default DoubleArraySymbol<S> pow(Function<S, Double[]> y) {
		return andThenAllNotNullEval(new Pow()::value, y);
	}

	default DoubleArraySymbol<S> pow(double p) {
		return andThenAllNotNullEval(new Power(p)::value);
	}

	default DoubleArraySymbol<S> root(Function<S, Double[]> y) {
		return pow(symbol(y).inverse());
	}

	default DoubleArraySymbol<S> signum() {
		return andThenAllNotNullEval(new Signum()::value);
	}

	default DoubleArraySymbol<S> sqrt() {
		return andThenAllNotNullEval(new Sqrt()::value);
	}

	default DoubleArraySymbol<S> subtract(Function<S, Double[]> y) {
		return andThenAllNotNullEval(new Subtract()::value, y);
	}

	default BooleanArraySymbol<S> allEquals(Function<S, Double[]> y) {
		return andThenAllNotNullEval(LogicalUtils::coequals, y, Boolean[]::new).asBooleanArray();
	}

	default BooleanArraySymbol<S> greater(boolean extrict, Function<S, Double[]> y) {
		if (extrict) {
			return andThenAllNotNullEval(LogicalUtils::extrictGreater, y, Boolean[]::new).asBooleanArray();
		} else {
			return andThenAllNotNullEval(LogicalUtils::greater, y, Boolean[]::new).asBooleanArray();
		}
	}

	default BooleanArraySymbol<S> lower(boolean extrict, Function<S, Double[]> y) {
		if (extrict) {
			return andThenAllNotNullEval(LogicalUtils::extrictLower, y, Boolean[]::new).asBooleanArray();
		} else {
			return andThenAllNotNullEval(LogicalUtils::lower, y, Boolean[]::new).asBooleanArray();
		}
	}

	default DoubleArraySymbol<S> max(Function<S, Double[]> y) {
		return andThenAllNotNullEval(new org.apache.commons.math3.analysis.function.Max()::value, y);
	}

	default DoubleArraySymbol<S> min(Function<S, Double[]> y) {
		return andThenAllNotNullEval(new org.apache.commons.math3.analysis.function.Min()::value, y);
	}

	default BooleanArraySymbol<S> allNotEquals(Function<S, Double[]> y) {
		return andThenAllNotNullEval(LogicalUtils::notCoequals, y, Boolean[]::new).asBooleanArray();
	}

	default BooleanArraySymbol<S> toLogical() {
		return andThenAllNotNullEval(LogicalUtils::parseBoolean, Boolean[]::new).asBooleanArray();
	}

	default DoubleArraySymbol<S> acos() {
		return andThenAllNotNullEval(new Acos()::value);
	}

	default DoubleArraySymbol<S> acosh() {
		return andThenAllNotNullEval(new Acosh()::value);
	}

	default DoubleArraySymbol<S> asin() {
		return andThenAllNotNullEval(new Asin()::value);
	}

	default DoubleArraySymbol<S> asinh() {
		return andThenAllNotNullEval(new Asinh()::value);
	}

	default DoubleArraySymbol<S> atan() {
		return andThenAllNotNullEval(new Atan()::value);
	}

	default DoubleArraySymbol<S> atan2(Function<S, Double[]> y) {
		return andThenAllNotNullEval(new Atan2()::value, y);
	}

	default DoubleArraySymbol<S> atanh() {
		return andThenAllNotNullEval(new Atanh()::value);
	}

	default DoubleArraySymbol<S> cos() {
		return andThenAllNotNullEval(new Cos()::value);
	}

	default DoubleArraySymbol<S> cosh() {
		return andThenAllNotNullEval(new Cosh()::value);
	}

	default DoubleArraySymbol<S> sin() {
		return andThenAllNotNullEval(new Sin()::value);
	}

	default DoubleArraySymbol<S> sinh() {
		return andThenAllNotNullEval(new Sinh()::value);
	}

	default DoubleArraySymbol<S> tan() {
		return andThenAllNotNullEval(new Tan()::value);
	}

	default DoubleArraySymbol<S> tanh() {
		return andThenAllNotNullEval(new Tanh()::value);
	}

	default DoubleArraySymbol<S> toDegrees() {
		return andThenAllNotNullEval(Math::toDegrees);
	}

	default DoubleArraySymbol<S> toRadians() {
		return andThenAllNotNullEval(Math::toRadians);
	}

	default DoubleArraySymbol<S> nullOmit() {
		return filter(isNull(), Double[]::new).asDoubleArray();
	}

	default DoublePrimitiveArraySymbol<S> toPrimitive() {
		return andThenAll(Double::doubleValue);
	}

}
