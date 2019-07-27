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

import org.utilities.core.lang.iterable.batch.IterablePipeBatch;
import org.utilities.core.lang.iterable.batch.IterablePipeRollBatch;
import org.utilities.core.lang.iterable.batch.Semaphore;
import org.utilities.core.lang.iterable.batch.SemaphoreImpl;
import org.utilities.core.lang.iterable.filter.Filter;
import org.utilities.core.lang.iterable.filter.FilterImpl;
import org.utilities.core.lang.iterable.filter.IterablePipeFilter;
import org.utilities.core.lang.iterable.limit.IterablePipeLimit;
import org.utilities.core.lang.iterable.limit.StopCriteria;
import org.utilities.core.lang.iterable.limit.StopCriteriaImpl;
import org.utilities.core.lang.iterable.map.IterablePipeMap;
import org.utilities.core.lang.iterable.map.IterablePipeParallelMap;
import org.utilities.core.lang.iterable.map.Mapper;
import org.utilities.core.lang.iterable.map.MapperImpl;
import org.utilities.core.lang.iterable.observer.IterablePipeObserved;
import org.utilities.core.lang.iterable.observer.Observer;
import org.utilities.core.lang.iterable.observer.ObserverImpl;
import org.utilities.core.lang.iterable.tracker.IterablePipeTracked;
import org.utilities.core.lang.iterable.tracker.Tracker;
import org.utilities.core.time.TicToc;
import org.utilities.core.util.concurrent.UtilitiesConcurrent;
import org.utilities.core.util.function.BiConsumerPlus;
import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.function.BiPredicatePlus;
import org.utilities.core.util.function.FunctionPlus;
import org.utilities.core.util.function.Pipeable;

public interface IterablePipe<T> extends Iterable<T>, Pipeable<Iterable<T>> {

	public static <T> IterablePipe<T> from(Supplier<? extends Iterator<T>> it) {
		return it::get;
	}

	public static <T, U> IterablePipe<U> from(Function<T, ? extends Iterator<U>> it, T t) {
		return from(FunctionPlus.parseSupplier(it, t));
	}

	@SuppressWarnings("unchecked")
	public static <T> IterablePipe<T> from(Class<T> type) {
		return IterablePipe.create();
	}

	@SuppressWarnings("unchecked")
	public static <T> IterablePipe<T> create(T... t) {
		return IterablePipe.newInstance(Arrays.asList(t));
	}

	public static <T> IterablePipe<T> newInstance(Iterable<T> it) {
		return it::iterator;
	}

	public static <T> IterablePipe<T> newInstance(IntFunction<T> get, IntSupplier length) {
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

	public default <R> IterablePipe<R> map(Mapper<T, R> mapper) {
		return new IterablePipeMap<>(this, mapper);
	}

	public default <R> IterablePipe<R> map(Function<T, R> mapper) {
		return map(new MapperImpl<>(mapper));
	}

	public default <U, R> IterablePipe<R> map(BiFunction<T, U, R> mapper, U u) {
		return map(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipe<R> map(BiFunction<T, U, R> mapper, Supplier<? extends U> u) {
		return map(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <R> IterablePipe<R> parallelMap(Mapper<T, R> mapper, int nThreads) {
		return new IterablePipeParallelMap<>(this, mapper, nThreads);
	}

	public default <R> IterablePipe<R> parallelMap(Mapper<T, R> mapper) {
		return new IterablePipeParallelMap<>(this, mapper, UtilitiesConcurrent.nThreads());
	}

	public default <R> IterablePipe<R> parallelMap(Function<T, R> mapper, int nThreads) {
		return parallelMap(new MapperImpl<>(mapper), nThreads);
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

	public default IterablePipe<T> track(Tracker<T> tracker) {
		return new IterablePipeTracked<>(this, tracker);
	}

	public default IterablePipeBatch<T> batch(Semaphore<T> semaphore) {
		return new IterablePipeBatch<>(this, semaphore);
	}

	public default IterablePipeBatch<T> batch(BiPredicate<T, T> semaphore) {
		return batch(new SemaphoreImpl<>(semaphore));
	}

	public default IterablePipeBatch<T> batch(Predicate<T> semaphore) {
		return batch((prev, current) -> semaphore.test(current));
	}

	public default <U> IterablePipeBatch<T> batch(BiPredicate<T, U> semaphore, U u) {
		return batch(BiPredicatePlus.parsePredicate(semaphore, u));
	}

	public default IterablePipeBatch<T> tuples(int dim) {
		return batch(Semaphore.tuples(dim));
	}

	public default IterablePipeBatch<T> interval(Function<T, Long> time, long window) {
		return batch(Semaphore.interval(time, window));
	}

	public default IterablePipeRollBatch<T> rollBatch(Semaphore<T> semaphore) {
		return new IterablePipeRollBatch<>(this, semaphore);
	}

	public default IterablePipeRollBatch<T> rollInterval(Function<T, Long> time, long window) {
		return rollBatch(Semaphore.interval(time, window));
	}

	public default IterablePipe<T> observe(Observer<T> observer) {
		return new IterablePipeObserved<>(this, observer);
	}

	public default IterablePipe<T> observe(Consumer<T> observer) {
		return observe(new ObserverImpl<>(observer));
	}

	public default <U> IterablePipe<T> observe(BiConsumer<T, U> observer, U u) {
		return observe(BiConsumerPlus.parseConsumer(observer, u));
	}

	public default IterablePipe<T> println(Function<T, String> toString) {
		return observe(Observer.println(toString));
	}

	public default IterablePipe<T> println() {
		return println(Object::toString);
	}

	public default IterablePipe<T> ticToc(String pattern) {
		return observe(Observer.ticToc(pattern));
	}

	public default IterablePipe<T> ticToc() {
		return ticToc(TicToc.DEFAULT_PATTERN);
	}

	public default IterablePipe<T> log(long frequency) {
		return observe(Observer.log(frequency));
	}

	public default IterablePipe<T> filter(Filter<T> filter) {
		return new IterablePipeFilter<>(this, filter);
	}

	public default IterablePipe<T> filter(Predicate<T> filter) {
		return new IterablePipeFilter<>(this, new FilterImpl<T>(filter));
	}

	public default <U> IterablePipe<T> filter(BiPredicate<T, U> filter, U u) {
		return filter(BiPredicatePlus.parsePredicate(filter, u));
	}

	public default IterablePipe<T> skip(int times) {
		return filter(Filter.skip(times));
	}

	public default IterablePipe<T> subsample(int by) {
		return filter(Filter.subsample(by));
	}

	public default IterablePipe<T> limit(StopCriteriaImpl<T> stop) {
		return new IterablePipeLimit<>(this, stop);
	}

	public default IterablePipe<T> limit(Predicate<T> stop) {
		return limit(new StopCriteriaImpl<>(stop));
	}

	public default <U> IterablePipe<T> limit(BiPredicate<T, U> stop, U u) {
		return limit(BiPredicatePlus.parsePredicate(stop, u));
	}

	public default IterablePipe<T> limit(int times) {
		return limit(StopCriteria.limit(times));
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
