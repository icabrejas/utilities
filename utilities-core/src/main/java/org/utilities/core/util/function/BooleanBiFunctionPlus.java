package org.utilities.core.util.function;

@FunctionalInterface
public interface BooleanBiFunctionPlus<R> {

	R apply(boolean t, boolean u);

}
