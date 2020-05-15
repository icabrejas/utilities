package org.utilities.symbolicmath.symbol;

import java.util.function.Function;

import org.utilities.symbolicmath.symbol.conditional.SymbolIfElse;
import org.utilities.symbolicmath.utils.LogicalUtils;

public interface SymbolBoolean<S> extends Symbol<S, Boolean> {

	static <S> SymbolBoolean<S> empty() {
		return constant(null);
	}

	static <S> SymbolBoolean<S> constant(Boolean constant) {
		return s -> constant;
	}

	static <S> SymbolBoolean<S> as(Function<S, Boolean> func) {
		if (func instanceof SymbolBoolean) {
			return (SymbolBoolean<S>) func;
		} else {
			return func::apply;
		}
	}

	default SymbolBoolean<S> not() {
		return as(andThenNotNullEval(LogicalUtils::not));
	}

	default SymbolDouble<S> toDouble() {
		return SymbolDouble.as(andThenNotNullEval(LogicalUtils::parseDouble));
	}

	default SymbolBoolean<S> and(Symbol<S, Boolean> y) {
		return as(andThenNotNullEval(Boolean::logicalAnd, y));
	}

	default SymbolBoolean<S> or(Symbol<S, Boolean> y) {
		return as(andThenNotNullEval(Boolean::logicalOr, y));
	}

	default SymbolBoolean<S> xor(Symbol<S, Boolean> y) {
		return as(andThenNotNullEval(Boolean::logicalXor, y));
	}

	default <R> Symbol<S, R> ifElse(Symbol<S, R> ifThen) {
		return new SymbolIfElse<>(this, ifThen);
	}

	default <R> Symbol<S, R> ifElse(Symbol<S, R> ifThen, Symbol<S, R> elseThen) {
		return new SymbolIfElse<>(this, ifThen, elseThen);
	}

}
