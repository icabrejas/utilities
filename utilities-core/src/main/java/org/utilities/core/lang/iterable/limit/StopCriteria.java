package org.utilities.core.lang.iterable.limit;

import org.utilities.core.lang.iterable.tracker.Tracker;
import org.utilities.core.util.lambda.LambdaInt;

public interface StopCriteria<T> extends Tracker<T> {

	boolean stop(T t);

	public static <T> StopCriteriaImpl<T> limit(int times) {
		LambdaInt counter = new LambdaInt();
		return new StopCriteriaImpl<T>(t -> times < counter.increment(1))//
				.start(() -> counter.set(0));
	}

}
