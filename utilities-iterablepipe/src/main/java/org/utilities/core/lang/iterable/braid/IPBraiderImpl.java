package org.utilities.core.lang.iterable.braid;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;

public class IPBraiderImpl<T> extends IPTrackerImpl<Map.Entry<Integer, T>> implements IPBraider<T> {

	private Comparator<Map.Entry<Integer, T>> selector;

	public IPBraiderImpl(Comparator<Entry<Integer, T>> selector) {
		super();
		this.selector = selector;
	}

	public IPBraiderImpl(Consumer<Integer> start, BiConsumer<Integer, Entry<Integer, T>> next, Consumer<Integer> end,
			Comparator<Entry<Integer, T>> selector) {
		super(start, next, end);
		this.selector = selector;
	}

	public IPBraiderImpl(IPTracker<Entry<Integer, T>> tracker, Comparator<Entry<Integer, T>> selector) {
		super(tracker);
		this.selector = selector;
	}

	@Override
	public int compare(Map.Entry<Integer, T> t1, Map.Entry<Integer, T> t2) {
		return selector.compare(t1, t2);
	}

	public IPBraiderImpl<T> compare(Comparator<Map.Entry<Integer, T>> comparator) {
		this.selector = comparator;
		return this;
	}

}
