package org.utilities.timeseries;

import java.util.Comparator;

import org.utilities.core.lang.iterable.observer.Observer;

public class TimeSeriesValidator implements Observer<Event> {

	private Event prev;
	private Comparator<Event> timeComparator = Event.timeComparator();

	@Override
	public void onStart() {
		prev = null;
	}

	@Override
	public void onEnd() {
	}

	@Override
	public void onNext(Event next) {
		if (prev != null && 0 <= timeComparator.compare(prev, next)) {
			// FIXME throw specific Exception
			throw new RuntimeException();
		}
		prev = next;
	}

}
