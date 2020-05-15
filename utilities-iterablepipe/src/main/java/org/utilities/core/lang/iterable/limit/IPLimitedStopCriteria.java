package org.utilities.core.lang.iterable.limit;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;
import org.utilities.core.util.function.AppendOpt;
import org.utilities.core.util.lambda.LambdaInt;

public interface IPLimitedStopCriteria<T> extends IPTracker<T> {

	boolean stop(int serialNumber, T t);

	public static <T> IPLimitedStopCriteriaImpl<T> concurrent(BiPredicate<Integer, T> stop) {
		return new IPLimitedStopCriteriaImpl<>(IPTracker.concurrent(), stop);
	}

	public static <T> IPLimitedStopCriteriaImpl<T> concurrent(Predicate<T> stop) {
		return new IPLimitedStopCriteriaImpl<>(IPTracker.concurrent(), stop);
	}

	public static <T> IPLimitedStopCriteria<T> limit(int times) {
		LambdaInt counter = new LambdaInt();
		IPTrackerImpl<T> tracker = IPTracker.concurrent();
		tracker.start(AppendOpt.After, () -> counter.set(0));
		return new IPLimitedStopCriteriaImpl<>(tracker, t -> times < counter.increment());
	}

}
