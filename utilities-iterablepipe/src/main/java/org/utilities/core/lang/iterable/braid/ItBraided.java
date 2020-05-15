package org.utilities.core.lang.iterable.braid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.track.IPTrackerNotifier;

public class ItBraided<T> implements Iterator<T> {

	public final int serialNumber;
	private List<? extends Iterator<T>> sources;
	private IPBraider<T> selector;
	private Map<Integer, T> cache;
	private IPTrackerNotifier<Map.Entry<Integer, T>> trackerNotifier;

	public ItBraided(int serialNumber, List<? extends Iterator<T>> sources, IPBraider<T> selector) {
		this.serialNumber = serialNumber;
		this.sources = sources;
		this.selector = selector;
		this.cache = new HashMap<>();
		this.trackerNotifier = new IPTrackerNotifier<>(serialNumber, selector);
	}

	@Override
	public boolean hasNext() {
		trackerNotifier.notifyStart();
		populateCurrent();
		boolean hasNext = !cache.isEmpty();
		if (!hasNext) {
			trackerNotifier.notifyEnd();
		}
		return hasNext;
	}

	private void populateCurrent() {
		int i = 0;
		for (Iterator<T> it : sources) {
			if (!cache.containsKey(i) && it.hasNext()) {
				cache.put(i, it.next());
			}
			i++;
		}
	}

	@Override
	public T next() {
		if (hasNext()) {
			Map.Entry<Integer, T> next = lowestIndex();
			cache.remove(next.getKey());
			selector.onNext(serialNumber, next);
			return next.getValue();
		} else {
			throw new NoSuchElementException();
		}
	}

	public Map.Entry<Integer, T> lowestIndex() {
		return Collections.min(cache.entrySet(), selector);
	}

}