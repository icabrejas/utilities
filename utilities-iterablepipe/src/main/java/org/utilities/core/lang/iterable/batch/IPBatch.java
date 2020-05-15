package org.utilities.core.lang.iterable.batch;

import java.util.Iterator;
import java.util.List;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.track.IPTracked;

public class IPBatch<T> implements IterablePipe<List<T>> {

	private int serialNumber;
	private IPTracked<T> it;
	private IPBatchSemaphore<T> semaphore;

	public IPBatch(Iterable<T> it, IPBatchSemaphore<T> semaphore) {
		this.it = new IPTracked<>(it, semaphore);
		this.semaphore = semaphore;
	}

	@Override
	public Iterator<List<T>> iterator() {
		return new ItBatch<>(serialNumber++, it.iterator(), semaphore);
	}

}
