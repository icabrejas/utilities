package org.utilities.core.lang.iterable.batch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// TODO
public class ItBatch<T> implements Iterator<List<T>> {

	private int serialNumber;
	private Iterator<T> it;
	private IPBatchSemaphore<T> semaphore;
	private List<T> batch = new ArrayList<>();
	private T current;

	public ItBatch(int serialNumber, Iterator<T> it, IPBatchSemaphore<T> semaphore) {
		this.serialNumber = serialNumber;
		this.it = it;
		this.semaphore = semaphore;
		fill();
	}

	@Override
	public boolean hasNext() {
		if (batch.isEmpty()) {
			fill();
		}
		return !batch.isEmpty();
	}

	private void fill() {
		if (current != null) {
			batch.add(current);
		}
		while (it.hasNext() && semaphore.store(serialNumber, current = it.next())) {
			batch.add(current);
			current = null;
		}
	}

	@Override
	public List<T> next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		List<T> next = new ArrayList<>(batch);
		batch.clear();
		return next;
	}

}