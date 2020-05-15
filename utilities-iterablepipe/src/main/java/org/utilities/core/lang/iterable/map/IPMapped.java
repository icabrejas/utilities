package org.utilities.core.lang.iterable.map;

import java.util.Iterator;

import org.utilities.core.lang.iterable.IterablePipe;

public class IPMapped<T, R> implements IterablePipe<R> {

	private int serialNumber;
	private Iterable<T> it;
	private IPMapper<T, R> mapper;

	public IPMapped(Iterable<T> it, IPMapper<T, R> mapper) {
		this.it = it;
		this.mapper = mapper;
	}

	@Override
	public Iterator<R> iterator() {
		return new ItMapped<T, R>(serialNumber++, it.iterator(), mapper);
	}

}
