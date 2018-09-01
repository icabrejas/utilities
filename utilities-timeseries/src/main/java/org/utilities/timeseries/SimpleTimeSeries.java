package org.utilities.timeseries;

import java.util.Iterator;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.observer.IterablePipeObserved;

public class SimpleTimeSeries<I> implements IterablePipe<SimpleEvent<I>> {

	private Iterable<SimpleEvent<I>> events;

	private SimpleTimeSeries(Iterable<SimpleEvent<I>> events) {
		this.events = events;
	}

	public static <I, V> SimpleTimeSeries<I> newInstance(Iterable<SimpleEvent<I>> events) {
		events = IterablePipeObserved.from(events, new SimpleTimeSeriesValidator<>());
		return new SimpleTimeSeries<>(events);
	}

	@Override
	public Iterator<SimpleEvent<I>> iterator() {
		return events.iterator();
	}

}
