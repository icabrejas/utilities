package org.utilities.core.lang.iterable.timeseries.simple;

import java.util.Iterator;

import org.utilities.core.lang.iterable.IterablePipe;

public class SimpleTimeSeries<I, V> implements IterablePipe<SimpleEvent<I, V>> {

	private String label;
	private Iterable<SimpleEvent<I, V>> events;

	public SimpleTimeSeries(String label, Iterable<SimpleEvent<I, V>> events) {
		this.label = label;
		this.events = events;
	}

	// TODO add catchAndRelease to validate label
	public static <I, V> SimpleTimeSeries<I, V> newInstance(String label, Iterable<SimpleEvent<I, V>> events) {
		return new SimpleTimeSeries<I, V>(label, events);
	}

	public String getLabel() {
		return label;
	}

	@Override
	public Iterator<SimpleEvent<I, V>> iterator() {
		return events.iterator();
	}

}
