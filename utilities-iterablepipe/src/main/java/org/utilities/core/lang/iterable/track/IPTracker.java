package org.utilities.core.lang.iterable.track;

import java.io.Closeable;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.function.Function;

import org.utilities.core.UtilitiesTime;
import org.utilities.core.time.TicToc;
import org.utilities.core.util.function.AppendOpt;
import org.utilities.core.util.function.Pipeable;
import org.utilities.core.util.function.RunnablePlus;
import org.utilities.core.util.lambda.LambdaInt;
import org.utilities.core.util.lambda.LambdaLong;

public interface IPTracker<T> extends Pipeable<IPTracker<T>> {

	void onStart(int serialNumber);

	void onNext(int serialNumber, T next);

	void onEnd(int serialNumber);

	public static <T> IPTrackerImpl<T> concurrent() {
		LambdaInt currentSerialNumber = new LambdaInt();
		return new IPTrackerImpl<T>()//
				.start(currentSerialNumber::set)
				.next((serialNumber, next) -> {
					if (currentSerialNumber.get() != serialNumber) {
						throw new ConcurrentModificationException(currentSerialNumber.get() + " != " + serialNumber);
					}
				})
				.end(serialNumber -> {
					if (currentSerialNumber.get() != serialNumber) {
						throw new ConcurrentModificationException();
					}
					currentSerialNumber.remove();
				});
	}

	public static <T> IPTrackerImpl<T> onStart(Runnable onStart) {
		IPTrackerImpl<T> tracker = concurrent();
		return tracker.start(onStart);
	}

	public static <T> IPTrackerImpl<T> onNext(Consumer<T> onNext) {
		IPTrackerImpl<T> tracker = concurrent();
		return tracker.next(onNext);
	}

	public static <T> IPTrackerImpl<T> onEnd(Runnable onEnd) {
		IPTrackerImpl<T> tracker = concurrent();
		return tracker.end(onEnd);
	}

	public static <T> IPTrackerImpl<T> println() {
		return println(Object::toString);
	}

	public static <T> IPTrackerImpl<T> println(Function<T, String> format) {
		return new IPTrackerImpl<T>()//
				.next(next -> System.out.println(format.apply(next)));
	}

	public static <T> IPTrackerImpl<T> ticToc() {
		return ticToc(TicToc.DEFAULT_PATTERN);
	}

	public static <T> IPTrackerImpl<T> ticToc(String pattern) {
		TicToc ticToc = UtilitiesTime.tic(pattern);
		IPTrackerImpl<T> tracker = IPTracker.concurrent();
		return tracker.start(AppendOpt.After, ticToc::tic)
				.next(AppendOpt.After, ticToc::toc)
				.end(AppendOpt.After, ticToc::toc);
	}

	public static <T> IPTracker<T> log(long frequency) {
		TicToc ticToc = UtilitiesTime.tic();
		LambdaLong counter = new LambdaLong();
		IPTrackerImpl<T> tracker = IPTracker.concurrent();
		return tracker.start(AppendOpt.After, () -> counter.set(0L))
				.start(AppendOpt.After, ticToc::tic)
				.next(AppendOpt.After, next -> {
					if (counter.increment() % frequency == 0) {
						ticToc.toc(counter.get() + ": ");
					}
				})
				.end(AppendOpt.After, () -> ticToc.toc(counter.get() + ": "));
	}

	// TODO move to utilities-io
	public static <T> IPTracker<T> closer(Closeable closeable) {
		IPTrackerImpl<T> tracker = IPTracker.concurrent();
		return tracker.end(AppendOpt.After, RunnablePlus.parseQuiet(closeable::close));
	}

}
