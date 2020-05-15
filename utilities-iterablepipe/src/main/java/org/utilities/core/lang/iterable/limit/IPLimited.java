package org.utilities.core.lang.iterable.limit;

import java.util.Iterator;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.track.IPTracked;

public class IPLimited<T> implements IterablePipe<T> {

	private int serialNumber;
	private IPTracked<T> it;
	private IPLimitedStopCriteria<T> stop;

	public IPLimited(Iterable<T> it, IPLimitedStopCriteria<T> stop) {
		this.it = new IPTracked<>(it, stop);
		this.stop = stop;
	}

	@Override
	public Iterator<T> iterator() {
		return new ItLimited<>(serialNumber++, it.iterator(), stop);
	}

}
