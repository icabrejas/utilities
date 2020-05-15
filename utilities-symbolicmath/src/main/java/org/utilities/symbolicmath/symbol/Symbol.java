package org.utilities.symbolicmath.symbol;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.util.function.Pipeable;
import org.utilities.symbolicmath.utils.ObjectUtils;
import org.utilities.symbolicmath.utils.function.BiFunctionUtils;
import org.utilities.symbolicmath.utils.function.FunctionUtils;

public interface Symbol<S, V> extends Function<S, V>, Pipeable<Symbol<S, V>> {

	static <S, V> Symbol<S, V> empty() {
		return constant(null);
	}

	static <S, V> Symbol<S, V> constant(V constant) {
		return s -> constant;
	}

	static <S, V> Symbol<S, V> as(Function<S, V> func) {
		return func::apply;
	}

	// FIXME remove this method
	default List<Symbol<S, ?>> dependencies() {
		return Collections.emptyList();
	}

	@Override
	default <W> Symbol<S, W> andThen(Function<? super V, ? extends W> after) {
		return as(Function.super.andThen(after));
	}

	default SymbolBoolean<S> isNull() {
		return SymbolBoolean.as(andThen(ObjectUtils::isNull));
	}

	default SymbolBoolean<S> isNotNull() {
		return SymbolBoolean.as(andThen(ObjectUtils::isNotNull));
	}

	default <W> Symbol<S, W> andThenNotNullEval(Function<V, W> after) {
		return andThen(FunctionUtils.notNullEval(after));
	}

	default <W, R> Symbol<S, R> andThen(BiFunction<V, W, R> after, Symbol<S, W> y) {
		return as(s -> after.apply(this.apply(s), y.apply(s)));
	}

	default <W, R> Symbol<S, R> andThenNotNullEval(BiFunction<V, W, R> after, Symbol<S, W> y) {
		return andThen(BiFunctionUtils.notNullEval(after), y);
	}

	default SymbolBoolean<S> isEquals(Symbol<S, V> y) {
		return SymbolBoolean.as(andThenNotNullEval(ObjectUtils::areEquals, y));
	}

	default SymbolBoolean<S> isNotEquals(Symbol<S, V> y) {
		return SymbolBoolean.as(andThenNotNullEval(ObjectUtils::areNotEquals, y));
	}

}
