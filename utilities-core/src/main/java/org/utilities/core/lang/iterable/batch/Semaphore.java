package org.utilities.core.lang.iterable.batch;

import java.util.function.Function;

import org.utilities.core.lang.UtilitiesNumber;
import org.utilities.core.lang.iterable.tracker.Tracker;
import org.utilities.core.util.lambda.LambdaInt;

public interface Semaphore<T> extends Tracker<T> {

	boolean store(T prev, T next);

	public static <T> SemaphoreImpl<T> tuples(int dim) {
		LambdaInt counter = new LambdaInt();
		return new SemaphoreImpl<T>((prev, next) -> {
			if (counter.increment(1) < dim) {
				return true;
			} else {
				counter.set(0);
				return false;
			}
		})//
				.start(() -> counter.set(0));
	}

	public static <T> SemaphoreImpl<T> interval(Function<T, Long> time, long window) {
		return new SemaphoreImpl<T>((prev, next) -> UtilitiesNumber.floor(time.apply(prev), window) == UtilitiesNumber
				.floor(time.apply(next), window));
	}

	public static <T> SemaphoreImpl<T> rollInterval(Function<T, Long> time, long window) {
		return new SemaphoreImpl<T>((prev, next) -> time.apply(next) - time.apply(prev) < window);
	}

}
