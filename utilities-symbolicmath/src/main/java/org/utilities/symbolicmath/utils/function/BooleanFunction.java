package org.utilities.symbolicmath.utils.function;

@FunctionalInterface
public interface BooleanFunction<R> {

	R apply(boolean value);

}
