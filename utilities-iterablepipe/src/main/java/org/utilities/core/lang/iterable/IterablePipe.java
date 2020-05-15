package org.utilities.core.lang.iterable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.utilities.core.UtilitiesConcurrent;
import org.utilities.core.lang.iterable.batch.IPBatch;
import org.utilities.core.lang.iterable.batch.IPBatchSemaphore;
import org.utilities.core.lang.iterable.filter.IPFilter;
import org.utilities.core.lang.iterable.filter.IPFiltered;
import org.utilities.core.lang.iterable.limit.IPLimited;
import org.utilities.core.lang.iterable.limit.IPLimitedStopCriteria;
import org.utilities.core.lang.iterable.map.IPMapped;
import org.utilities.core.lang.iterable.map.IPMappedParallelly;
import org.utilities.core.lang.iterable.map.IPMapper;
import org.utilities.core.lang.iterable.track.IPTracked;
import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.util.function.BiConsumerPlus;
import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.function.BiPredicatePlus;
import org.utilities.core.util.function.FunctionPlus;
import org.utilities.core.util.function.Pipeable;

public interface IterablePipe<T> extends Iterable<T>, Pipeable<Iterable<T>> {

	public static <T> IterablePipe<T> create(Supplier<? extends Iterator<T>> it) {
		return it::get;
	}

	public static <T, U> IterablePipe<U> create(Function<T, ? extends Iterator<U>> it, T t) {
		return create(FunctionPlus.parseSupplier(it, t));
	}

	public static <T> IterablePipe<T> create(Iterable<T> it) {
		if (it instanceof IterablePipe) {
			return (IterablePipe<T>) it;
		} else {
			return it::iterator;
		}
	}

	public static <T> IterablePipe<T> create(IntFunction<T> get, IntSupplier length) {
		return () -> new Iterator<T>() {

			private int i;

			@Override
			public boolean hasNext() {
				return i < length.getAsInt();
			}

			@Override
			public T next() {
				return get.apply(i++);
			}
		};
	}

	public default <R> IterablePipe<R> map(IPMapper<T, R> mapper) {
		return new IPMapped<>(this, mapper);
	}

	public default <R> IterablePipe<R> map(Function<T, R> mapper) {
		return map(IPMapper.concurrent(mapper));
	}

	public default <U, R> IterablePipe<R> map(BiFunction<T, U, R> mapper, U u) {
		return map(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipe<R> map(BiFunction<T, U, R> mapper, Supplier<? extends U> u) {
		return map(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <R> IterablePipe<R> parallelMap(IPMapper<T, R> mapper, int nThreads) {
		return new IPMappedParallelly<>(this, mapper, nThreads);
	}

	public default <R> IterablePipe<R> parallelMap(IPMapper<T, R> mapper) {
		return new IPMappedParallelly<>(this, mapper, UtilitiesConcurrent.nThreads());
	}

	public default <R> IterablePipe<R> parallelMap(Function<T, R> mapper, int nThreads) {
		return parallelMap(IPMapper.concurrent(mapper), nThreads);
	}

	public default <R> IterablePipe<R> parallelMap(Function<T, R> mapper) {
		return parallelMap(mapper, UtilitiesConcurrent.nThreads());
	}

	public default <R> IterablePipe<R> flatMap(Function<T, ? extends Iterable<R>> mapper) {
		return new IterablePipeFlat<>(map(mapper));
	}

	public default <U, R> IterablePipe<R> flatMap(BiFunction<T, U, Iterable<R>> mapper, U u) {
		return flatMap(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipe<R> flatMap(BiFunction<T, U, Iterable<R>> mapper, Supplier<? extends U> u) {
		return flatMap(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipe<R> flatMap(BiFunction<T, U, Iterable<R>> mapper, Function<T, ? extends U> u) {
		return flatMap(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <R> IterablePipe<R> parallelFlatMap(Function<T, ? extends Iterable<R>> mapper) {
		return new IterablePipeFlat<>(parallelMap(mapper));
	}

	public default <R> IterablePipe<R> parallelFlatMap(Function<T, ? extends Iterable<R>> mapper, int nThreads) {
		return new IterablePipeFlat<>(parallelMap(mapper, nThreads));
	}

	public default IterablePipe<T> bind(Iterable<T> it) {
		return new IterablePipeFlat<>(Arrays.asList(this, it));
	}

	public default IterablePipe<T> track(IPTracker<T> tracker) {
		return new IPTracked<>(this, tracker);
	}

	public default IterablePipe<T> trackOnStart(Runnable onStart) {
		return track(IPTracker.onStart(onStart));
	}

	public default IterablePipe<T> trackOnNext(Consumer<T> onNext) {
		return track(IPTracker.onNext(onNext));
	}

	public default IterablePipe<T> trackOnEnd(Runnable onEnd) {
		return track(IPTracker.onEnd(onEnd));
	}

	public default IPBatch<T> batch(IPBatchSemaphore<T> semaphore) {
		return new IPBatch<>(this, semaphore);
	}

	public default IPBatch<T> batch(BiPredicate<T, T> semaphore) {
		return batch(IPBatchSemaphore.batches(semaphore));
	}

	public default IPBatch<T> batch(Predicate<T> semaphore) {
		return batch(IPBatchSemaphore.concurrent(semaphore));
	}

	public default <U> IPBatch<T> batch(BiPredicate<T, U> semaphore, U u) {
		return batch(BiPredicatePlus.parsePredicate(semaphore, u));
	}

	public default IterablePipe<T> filter(IPFilter<T> filter) {
		return new IPFiltered<>(this, filter);
	}

	public default IterablePipe<T> filter(Predicate<T> filter) {
		return filter(IPFilter.concurrent(filter));
	}

	public default <U> IterablePipe<T> filter(BiPredicate<T, U> filter, U u) {
		return filter(BiPredicatePlus.parsePredicate(filter, u));
	}

	public default IterablePipe<T> limit(IPLimitedStopCriteria<T> stop) {
		return new IPLimited<>(this, stop);
	}

	public default IterablePipe<T> limit(Predicate<T> stop) {
		return limit(IPLimitedStopCriteria.concurrent(stop));
	}

	public default <U> IterablePipe<T> limit(BiPredicate<T, U> stop, U u) {
		return limit(BiPredicatePlus.parsePredicate(stop, u));
	}

	public default <U> void forEach(BiConsumer<T, U> biconsumer, U u) {
		forEach(BiConsumerPlus.parseConsumer(biconsumer, u));
	}

	public default List<T> toList() {
		return UtilitiesIterable.toList(this);
	}

	public default Set<T> toSet() {
		return UtilitiesIterable.toSet(this);
	}

}
