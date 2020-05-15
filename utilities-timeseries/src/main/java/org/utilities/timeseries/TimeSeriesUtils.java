package org.utilities.timeseries;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;
import org.utilities.core.util.function.AppendOpt;
import org.utilities.core.util.lambda.LambdaValue;

public class TimeSeriesUtils {

	public static IPTracker<Event> validator() {
		LambdaValue<Event> prev = new LambdaValue<>();
		IPTrackerImpl<Event> concurrent = IPTracker.concurrent();
		return concurrent.start(AppendOpt.After, prev::remove)
				.next(next -> {
					if (next.getTime() == null || (prev.isPresent() && 0 <= next.compareTimeTo(prev.get()))) {
						throw new Error();
					}
					prev.set(next);
				});
	}

}
