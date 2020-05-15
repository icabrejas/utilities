package org.utilities.core.lang.iterable.filter;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;
import org.utilities.core.util.lambda.LambdaInt;

public interface IPFilter<T> extends IPTracker<T> {

	boolean emit(int serialNumber, T t);

	public static <T> IPFilterImpl<T> concurrent(BiPredicate<Integer, T> emit) {
		return new IPFilterImpl<>(IPTracker.concurrent(), emit);
	}

	public static <T> IPFilterImpl<T> concurrent(Predicate<T> emit) {
		return new IPFilterImpl<>(IPTracker.concurrent(), emit);
	}

	public static <T> IPFilter<T> skip(int times) {
		LambdaInt counter = new LambdaInt();
		IPTrackerImpl<T> tracker = IPTracker.concurrent();
		tracker.start(() -> counter.set(0));
		return new IPFilterImpl<T>(tracker, t -> times < counter.increment());
	}

	public static <T> IPFilter<T> subsample(int by) {
		LambdaInt counter = new LambdaInt();
		IPTrackerImpl<T> tracker = IPTracker.concurrent();
		tracker.start(() -> counter.set(-1));
		return new IPFilterImpl<T>(tracker, t -> counter.increment() % by == 0);
	}

}
