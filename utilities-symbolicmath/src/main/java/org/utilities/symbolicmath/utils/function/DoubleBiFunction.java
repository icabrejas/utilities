package org.utilities.symbolicmath.utils.function;

@FunctionalInterface
public interface DoubleBiFunction<R> {

	R apply(double t, double u);

}
