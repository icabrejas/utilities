package org.utilities.timeseries;

import java.util.Comparator;

import org.utilities.core.lang.iterable.observer.Observer;

public class SimpleTimeSeriesValidator<I> implements Observer<SimpleEvent<I>> {

	private SimpleEvent<I> prev;
	private Comparator<SimpleEvent<I>> timeComparator = SimpleEvent.timeComparator();

	@Override
	public void onStart() {
		prev = null;
	}

	@Override
	public void onEnd() {
	}

	@Override
	public void onNext(SimpleEvent<I> next) {
		if (prev != null && (!next.infoEquals(prev) || 0 <= timeComparator.compare(prev, next))) {
			// FIXME throw specific Exception
			throw new RuntimeException();
		}
		prev = next;
	}

}
