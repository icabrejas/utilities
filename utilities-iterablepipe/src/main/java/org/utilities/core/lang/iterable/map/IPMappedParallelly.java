package org.utilities.core.lang.iterable.map;

import java.util.Iterator;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.track.IPTracked;

public class IPMappedParallelly<T, R> implements IterablePipe<R> {

	private int serialNumber;
	private IPTracked<T> it;
	private IPMapper<T, R> mapper;
	private int nThreads;

	public IPMappedParallelly(Iterable<T> it, IPMapper<T, R> mapper, int nThreads) {
		this.it = new IPTracked<>(it, mapper);
		this.mapper = mapper;
		this.nThreads = nThreads;
	}

	@Override
	public Iterator<R> iterator() {
		return new ItMappedParallelly<>(serialNumber++, it.iterator(), mapper, nThreads);
	}

}
