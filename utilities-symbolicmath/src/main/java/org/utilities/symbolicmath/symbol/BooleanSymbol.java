package org.utilities.symbolicmath.symbol;

import java.util.function.Function;

import org.utilities.symbolicmath.symbol.conditional.IfElse;
import org.utilities.symbolicmath.utils.LogicalUtils;
import org.utilities.symbolicmath.utils.ObjectUtils;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;
import org.utilities.symbolicmath.utils.function.BooleanBiFunction;
import org.utilities.symbolicmath.utils.function.BooleanFunction;
import org.utilities.symbolicmath.utils.function.FunctionUtils;

public interface BooleanSymbol<S> extends Symbol<S, Boolean> {

	static <S> BooleanSymbol<S> symbol(Function<S, Boolean> func) {
		if (func instanceof BooleanSymbol) {
			return (BooleanSymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> BooleanSymbol<S> constant(Boolean constant) {
		return store -> constant;
	}

	default <R> Symbol<S, R> andThenNotNullEval(BooleanFunction<R> after) {
		return andThen(FunctionUtils.notNullEval(after::apply));
	}

	default <R> Symbol<S, R> andThenNotNullEval(BooleanBiFunction<R> after, Function<S, Boolean> y) {
		return andThen(BiFunctionUtils.notNullEval(after::apply), y);
	}

	default BooleanSymbol<S> and(Function<S, Boolean> y) {
		return andThenNotNullEval(Boolean::logicalAnd, y).asBoolean();
	}

	default BooleanSymbol<S> isNotNull() {
		return andThen(ObjectUtils::isNotNull).asBoolean();
	}

	default BooleanSymbol<S> isNull() {
		return andThen(ObjectUtils::isNull).asBoolean();
	}

	default BooleanSymbol<S> not() {
		return andThenNotNullEval(LogicalUtils::not).asBoolean();
	}

	default BooleanSymbol<S> or(Function<S, Boolean> y) {
		return andThenNotNullEval(Boolean::logicalOr, y).asBoolean();
	}

	default BooleanSymbol<S> xor(Function<S, Boolean> y) {
		return andThenNotNullEval(Boolean::logicalXor, y).asBoolean();
	}

	default <R> Symbol<S, R> ifElse(Symbol<S, R> ifThen) {
		return new IfElse<>(this, ifThen);
	}

	default <R> Symbol<S, R> ifElse(Symbol<S, R> ifThen, Symbol<S, R> elseThen) {
		return new IfElse<>(this, ifThen, elseThen);
	}

	default DoubleSymbol<S> toDouble() {
		return andThen(FunctionUtils.notNullEval(LogicalUtils::parseDouble)).asDouble();
	}

}
