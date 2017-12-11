package org.utilities.core.lang.iterable.batch;

import java.util.function.BiPredicate;
import java.util.function.Function;

import org.utilities.core.lang.UtilitiesNumber;
import org.utilities.core.lang.iterable.tracker.TrackerImpl;
import org.utilities.core.util.function.BiPredicatePlus;

public class SemaphoreImpl<T> extends TrackerImpl<T> implements Semaphore<T> {

	private BiPredicate<T, T> store = BiPredicatePlus.dummy();

	@Override
	public SemaphoreImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public boolean store(T prev, T next) {
		return store.test(prev, next);
	}

	public SemaphoreImpl<T> store(BiPredicate<T, T> store) {
		this.store = store;
		return this;
	}

	@Override
	public SemaphoreImpl<T> end(Runnable end) {
		super.end(end);
		return this;
	}

	public static <T> SemaphoreImpl<T> tuples(int dim) {
		return new SemaphoreImpl<T>() {

			private Integer counter;

			@Override
			public void onStart() {
				counter = 0;
			}

			@Override
			public boolean store(T prev, T next) {
				if (++counter <= dim) {
					return true;
				} else {
					counter = 1;
					return false;
				}
			}

		};
	}

	public static <T> SemaphoreImpl<T> interval(Function<T, Long> time, long window) {
		return new SemaphoreImpl<T>().store((T prev, T next) -> UtilitiesNumber.floor(time.apply(prev),
				window) == UtilitiesNumber.floor(time.apply(next), window));
	}

	public static <T> SemaphoreImpl<T> rollInterval(Function<T, Long> time, long window) {
		return new SemaphoreImpl<T>().store((T prev, T next) -> time.apply(next) - time.apply(prev) < window);
	}

}
