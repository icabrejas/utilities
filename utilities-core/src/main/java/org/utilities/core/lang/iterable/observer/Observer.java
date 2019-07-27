package org.utilities.core.lang.iterable.observer;

import java.util.function.Function;

import org.utilities.core.lang.iterable.tracker.Tracker;
import org.utilities.core.time.TicToc;
import org.utilities.core.time.UtilitiesTime;
import org.utilities.core.util.lambda.LambdaLong;

public interface Observer<T> extends Tracker<T> {

	void onNext(T next);

	public static <T> ObserverImpl<T> println(Function<T, String> toString) {
		return new ObserverImpl<>(t -> System.out.println(toString.apply(t)));
	}

	public static <T> ObserverImpl<T> ticToc(String pattern) {
		TicToc ticToc = UtilitiesTime.tic(pattern);
		return new ObserverImpl<T>(t -> ticToc.toc())//
				.start(ticToc::tic)
				.end(ticToc::toc);
	}

	public static <T> ObserverImpl<T> log(long frequency) {
		TicToc ticToc = UtilitiesTime.tic();
		LambdaLong counter = new LambdaLong();
		return new ObserverImpl<T>(t -> {
			if (counter.add(1) % frequency == 0) {
				System.out.print(counter.get() + ": ");
				ticToc.toc();
			}
		})//
				.start(() -> {
					counter.set(0L);
					ticToc.tic();
				})
				.end(() -> {
					System.out.print(counter.get() + ": ");
					ticToc.toc();
				});
	}

}
