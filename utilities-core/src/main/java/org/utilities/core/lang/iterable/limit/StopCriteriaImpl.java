package org.utilities.core.lang.iterable.limit;

import java.util.function.Predicate;

import org.utilities.core.lang.iterable.tracker.TrackerImpl;
import org.utilities.core.util.lambda.LambdaInt;

public class StopCriteriaImpl<T> extends TrackerImpl<T> implements StopCriteria<T> {

	private Predicate<T> stop;

	@Override
	public StopCriteriaImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public boolean stop(T t) {
		return stop.test(t);
	}

	public StopCriteriaImpl<T> stop(Predicate<T> stop) {
		this.stop = stop;
		return this;
	}

	@Override
	public StopCriteriaImpl<T> end(Runnable end) {
		super.end(end);
		return this;
	}

	public static <T> StopCriteriaImpl<T> limit(int times) {
		LambdaInt counter = new LambdaInt();
		return new StopCriteriaImpl<T>().start(() -> counter.set(0))
				.stop((T t) -> times < counter.add(1));
	}
	
}
