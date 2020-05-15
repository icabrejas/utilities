package org.utilities.core.lang.iterable.batch;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;
import org.utilities.core.util.lambda.LambdaInt;
import org.utilities.core.util.lambda.LambdaValue;

public interface IPBatchSemaphore<T> extends IPTracker<T> {

	boolean store(int serialNumber, T t);

	public static <T> IPBatchSemaphoreImpl<T> concurrent(BiPredicate<Integer, T> store) {
		return new IPBatchSemaphoreImpl<>(IPTracker.concurrent(), store);
	}

	public static <T> IPBatchSemaphoreImpl<T> concurrent(Predicate<T> store) {
		return new IPBatchSemaphoreImpl<>(IPTracker.concurrent(), store);
	}

	public static <T> IPBatchSemaphore<T> tuples(int dim) {
		LambdaInt counter = new LambdaInt();
		IPTrackerImpl<T> tracker = IPTracker.concurrent();
		tracker.start(() -> counter.set(0));
		return new IPBatchSemaphoreImpl<>(tracker, t -> {
			if (counter.increment() < dim) {
				return true;
			} else {
				counter.set(0);
				return false;
			}
		});
	}

	public static <T> IPBatchSemaphore<T> batches(BiPredicate<T, T> store) {
		LambdaValue<T> first = new LambdaValue<>();
		IPTrackerImpl<T> tracker = IPTracker.concurrent();
		tracker.start(() -> first.set(null));
		return new IPBatchSemaphoreImpl<T>(tracker, t -> {
			boolean haveToStore = store.test(first.get(), t);
			if (!haveToStore) {
				first.set(t);
			}
			return haveToStore;
		});
	}

}
