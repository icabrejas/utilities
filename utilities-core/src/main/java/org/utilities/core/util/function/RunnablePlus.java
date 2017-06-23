package org.utilities.core.util.function;

public interface RunnablePlus extends Runnable {

	public static RunnablePlus dummy() {
		return () -> {
		};
	}
	
}
