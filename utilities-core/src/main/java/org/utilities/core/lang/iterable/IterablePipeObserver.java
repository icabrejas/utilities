package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.function.Consumer;

import org.utilities.core.time.TicToc;
import org.utilities.core.time.UtilitiesTime;
import org.utilities.core.util.lambda.LambdaLong;
import org.utilities.core.util.lambda.LambdaValue;

// FIXME use tracker
public class IterablePipeObserver<T> implements IterablePipe<T> {

	private Iterable<T> it;
	private Consumer<T> observer;

	public IterablePipeObserver(Iterable<T> it, Consumer<T> observer) {
		this.it = it;
		this.observer = observer;
	}

	@Override
	public Iterator<T> iterator() {
		return new It<>(it.iterator(), observer);
	}

	// FIXME use tracker and print when iterator is empty
	public static <T> Consumer<T> ticTocObserver(String pattern) {
		return new Consumer<T>() {

			private TicToc tic;

			@Override
			public void accept(T t) {
				if (tic == null) {
					tic = UtilitiesTime.tic(pattern);
				}
				tic.toc();
				tic = UtilitiesTime.tic(pattern);
			}
		};
	}

	// FIXME use tracker
	public static <T> Consumer<T> logObserver(long frequency) {
		LambdaValue<TicToc> tic = new LambdaValue<TicToc>(UtilitiesTime.tic());
		LambdaLong counter = new LambdaLong(0L);
		return evt -> {
			if (counter.add(1) % frequency == 0) {
				System.out.print(counter.get() + ": ");
				TicToc ticToc = tic.get();
				ticToc.toc();
				tic.set(UtilitiesTime.tic());
			}
		};
	}

	private static class It<T> implements Iterator<T> {

		private Iterator<T> it;
		private Consumer<T> observer;

		public It(Iterator<T> it, Consumer<T> observer) {
			this.it = it;
			this.observer = observer;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public T next() {
			T next = it.next();
			observer.accept(next);
			return next;
		}

	}

}
