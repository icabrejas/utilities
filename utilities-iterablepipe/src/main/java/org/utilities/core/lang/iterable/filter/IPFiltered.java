package org.utilities.core.lang.iterable.filter;

import java.util.Iterator;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.track.IPTracked;

public class IPFiltered<T> implements IterablePipe<T> {

	private int serialNumber;
	private IPTracked<T> it;
	private IPFilter<T> filter;

	public IPFiltered(Iterable<T> it, IPFilter<T> filter) {
		this.it = new IPTracked<>(it, filter);
		this.filter = filter;
	}

	@Override
	public Iterator<T> iterator() {
		return new ItFiltered<>(serialNumber++, it.iterator(), filter);
	}

}
