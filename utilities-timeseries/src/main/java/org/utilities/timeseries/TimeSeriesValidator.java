package org.utilities.timeseries;

import java.util.Comparator;

import org.utilities.core.lang.iterable.observer.Observer;

public class TimeSeriesValidator<I> implements Observer<Event<I>> {

	private Event<I> prev;
	private Comparator<Event<I>> timeComparator = Event.timeComparator();

	@Override
	public void onStart() {
		prev = null;
	}

	@Override
	public void onEnd() {
	}

	@Override
	public void onNext(Event<I> next) {
		if (prev != null && (!next.infoEquals(prev) || 0 <= timeComparator.compare(prev, next))) {
			// FIXME throw specific Exception
			throw new RuntimeException();
		}
		prev = next;
	}

}
