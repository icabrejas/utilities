package org.utilities.core.lang.iterable.map;

import java.util.Iterator;

import org.utilities.core.lang.iterable.track.ItTracked;

public class ItMapped<T, R> implements Iterator<R> {

	private int serialNumber;
	private Iterator<T> it;
	private IPMapper<T, R> mapper;

	public ItMapped(int serialNumber, Iterator<T> it, IPMapper<T, R> mapper) {
		this.serialNumber = serialNumber;
		this.it = new ItTracked<>(serialNumber, it, mapper);
		this.mapper = mapper;
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public R next() {
		return mapper.map(serialNumber, it.next());
	}

}
