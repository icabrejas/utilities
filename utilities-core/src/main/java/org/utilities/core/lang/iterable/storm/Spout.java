package org.utilities.core.lang.iterable.storm;

import java.util.Iterator;
import java.util.function.Supplier;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.util.concurrent.UtilitiesThread;

public class Spout<T> implements IterablePipe<T> {

	private Supplier<T> spout;
	private long millis = 100;

	public Spout(Supplier<T> spout) {
		this.spout = spout;
	}

	public Spout(Supplier<T> spout, long millis) {
		this.spout = spout;
		this.millis = millis;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(spout, millis);
	}

	private static class It<T> implements Iterator<T> {

		private Supplier<T> spout;
		private long millis;
		private T next;

		public It(Supplier<T> spout, long millis) {
			this.spout = spout;
			this.millis = millis;
		}

		@Override
		public boolean hasNext() {
			while (next == null) {
				next = spout.get();
				UtilitiesThread.sleepQuietly(millis);
			}
			return true;
		}

		@Override
		public T next() {
			T next = this.next;
			return next;
		}

	}

}
