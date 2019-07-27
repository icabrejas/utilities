package org.utilities.symbolicmath.utils.function;

@FunctionalInterface
public interface BooleanBiFunction<R> {

	R apply(boolean t, boolean u);

}
