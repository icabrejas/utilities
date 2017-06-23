package org.utilities.io;

import java.io.Closeable;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

//FIXME use iteratorTracker.closer
public class IteratorCloseable<E> implements Iterator<E>, Closeable {

	private Iterator<E> iterator;
	private Closeable closeable;
	private boolean finished;

	public IteratorCloseable(Iterator<E> iterator, Closeable closeable) {
		this.iterator = iterator;
		this.closeable = closeable;
	}

	@Override
	public boolean hasNext() {
		if (finished) {
			return false;
		} else {
			boolean hasNext = iterator.hasNext();
			if (!hasNext) {
				close();
			}
			return hasNext;
		}

	}

	@Override
	public E next() {
		return iterator.next();
	}

	@Override
	public void close() {
		if (!finished) {
			finished = true;
			IOUtils.closeQuietly(closeable);
		}
	}

}
