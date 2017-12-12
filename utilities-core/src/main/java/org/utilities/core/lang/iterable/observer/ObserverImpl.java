package org.utilities.core.lang.iterable.observer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.utilities.core.lang.iterable.tracker.TrackerImpl;
import org.utilities.core.time.TicToc;
import org.utilities.core.time.UtilitiesTime;
import org.utilities.core.util.function.BiConsumerPlus;
import org.utilities.core.util.function.ConsumerPlus;
import org.utilities.core.util.lambda.LambdaLong;

public class ObserverImpl<T> extends TrackerImpl<T> implements Observer<T> {

	private Consumer<T> next = ConsumerPlus.dummy();

	@Override
	public ObserverImpl<T> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public void onNext(T next) {
		this.next.accept(next);
	}

	public ObserverImpl<T> next(Consumer<T> next) {
		this.next = next;
		return this;
	}

	public <U> ObserverImpl<T> next(BiConsumer<T, U> next, U u) {
		this.next = BiConsumerPlus.parseConsumer(next, u);
		return this;
	}

	@Override
	public ObserverImpl<T> end(Runnable end) {
		super.end(end);
		return this;
	}


	public static <T> ObserverImpl<T> println(Function<T, String> toString) {
		return new ObserverImpl<T>().next((T t) -> System.out.println(toString.apply(t)));
	}

	public static <T> ObserverImpl<T> ticToc(String pattern) {
		TicToc ticToc = UtilitiesTime.tic(pattern);
		return new ObserverImpl<T>().start(ticToc::tic)
				.next((T t) -> ticToc.toc())
				.end(ticToc::toc);
	}

	public static <T> ObserverImpl<T> log(long frequency) {
		TicToc ticToc = UtilitiesTime.tic();
		LambdaLong counter = new LambdaLong();
		return new ObserverImpl<T>().start(() -> {
			counter.set(0L);
			ticToc.tic();
		})
				.next((T t) -> {
					if (counter.add(1) % frequency == 0) {
						System.out.print(counter.get() + ": ");
						ticToc.toc();
					}
				})
				.end(() -> {
					System.out.print(counter.get() + ": ");
					ticToc.toc();
				});
	}

}
