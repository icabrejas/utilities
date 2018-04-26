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
import org.utilities.core.util.concurrent.UtilitiesThread;
import org.utilities.core.util.function.BiConsumerPlus;
import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.function.BiPredicatePlus;
import org.utilities.core.util.function.FunctionPlus;
import org.utilities.core.util.function.Pipeline;

public interface IterablePipe<T> extends Iterable<T> {

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
		return IterablePipe.from(Arrays.asList(t));
	}

	public static <T> IterablePipe<T> from(Iterable<T> it) {
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

	public default <R> IterablePipeMap<T, R> map(Mapper<T, R> mapper) {
		return new IterablePipeMap<>(this, mapper);
	}

	public default <R> IterablePipeMap<T, R> map(Function<T, R> mapper) {
		return map(new MapperImpl<T, R>().map(mapper));
	}

	public default <U, R> IterablePipeMap<T, R> map(BiFunction<T, U, R> mapper, U u) {
		return map(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipeMap<T, R> map(BiFunction<T, U, R> mapper, Supplier<? extends U> u) {
		return map(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipeMap<T, R> map(BiFunction<T, U, R> mapper, Function<T, ? extends U> u) {
		return map(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <R> IterablePipe<R> parallelMap(Mapper<T, R> mapper, int nThreads) {
		return new IterablePipeParallelMap<>(this, mapper, nThreads);
	}

	public default <R> IterablePipe<R> parallelMap(Mapper<T, R> mapper) {
		return new IterablePipeParallelMap<>(this, mapper, UtilitiesThread.nThreads());
	}

	public default <R> IterablePipe<R> parallelMap(Function<T, R> mapper, int nThreads) {
		return parallelMap(new MapperImpl<T, R>().map(mapper), nThreads);
	}

	public default <R> IterablePipe<R> parallelMap(Function<T, R> mapper) {
		return parallelMap(mapper, UtilitiesThread.nThreads());
	}

	public default <R> IterablePipeFlat<R> flatMap(Function<T, ? extends Iterable<R>> mapper) {
		return new IterablePipeFlat<>(map(mapper));
	}

	public default <U, R> IterablePipeFlat<R> flatMap(BiFunction<T, U, Iterable<R>> mapper, U u) {
		return flatMap(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipeFlat<R> flatMap(BiFunction<T, U, Iterable<R>> mapper, Supplier<? extends U> u) {
		return flatMap(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipeFlat<R> flatMap(BiFunction<T, U, Iterable<R>> mapper,
			Function<T, ? extends U> u) {
		return flatMap(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <R> IterablePipeFlat<R> parallelFlatMap(Function<T, ? extends Iterable<R>> mapper) {
		return new IterablePipeFlat<>(parallelMap(mapper));
	}

	public default <R> IterablePipeFlat<R> parallelFlatMap(Function<T, ? extends Iterable<R>> mapper, int nThreads) {
		return new IterablePipeFlat<>(parallelMap(mapper, nThreads));
	}

	public default IterablePipeFlat<T> bind(Iterable<T> it) {
		return new IterablePipeFlat<>(Arrays.asList(this, it));
	}

	public default IterablePipeTracked<T> track(Tracker<T> tracker) {
		return new IterablePipeTracked<>(this, tracker);
	}

	public default IterablePipeBatch<T> batch(Semaphore<T> semaphore) {
		return new IterablePipeBatch<>(this, semaphore);
	}

	public default IterablePipeBatch<T> batch(BiPredicate<T, T> semaphore) {
		return batch(new SemaphoreImpl<T>().store(semaphore));
	}

	public default IterablePipeBatch<T> batch(Predicate<T> semaphore) {
		return batch((prev, current) -> semaphore.test(current));
	}

	public default <U> IterablePipeBatch<T> batch(BiPredicate<T, U> semaphore, U u) {
		return batch(BiPredicatePlus.parsePredicate(semaphore, u));
	}

	public default IterablePipeBatch<T> tuples(int dim) {
		return batch(SemaphoreImpl.tuples(dim));
	}

	public default IterablePipeBatch<T> interval(Function<T, Long> time, long window) {
		return batch(SemaphoreImpl.interval(time, window));
	}

	public default IterablePipeRollBatch<T> rollBatch(Semaphore<T> semaphore) {
		return new IterablePipeRollBatch<>(this, semaphore);
	}

	public default IterablePipeRollBatch<T> rollInterval(Function<T, Long> time, long window) {
		return rollBatch(SemaphoreImpl.interval(time, window));
	}

	public default IterablePipeObserved<T> observe(Observer<T> observer) {
		return new IterablePipeObserved<>(this, observer);
	}

	public default IterablePipeObserved<T> observe(Consumer<T> observer) {
		return observe(new ObserverImpl<T>().next(observer));
	}

	public default <U> IterablePipeObserved<T> observe(BiConsumer<T, U> observer, U u) {
		return observe(BiConsumerPlus.parseConsumer(observer, u));
	}

	public default IterablePipeObserved<T> println(Function<T, String> toString) {
		return observe(ObserverImpl.println(toString));
	}

	public default IterablePipeObserved<T> println() {
		return println(Object::toString);
	}

	public default IterablePipeObserved<T> ticToc(String pattern) {
		return observe(ObserverImpl.ticToc(pattern));
	}

	public default IterablePipeObserved<T> ticToc() {
		return ticToc(TicToc.DEFAULT_PATTERN);
	}

	public default IterablePipe<T> log(long frequency) {
		return observe(ObserverImpl.log(frequency));
	}

	public default IterablePipeFilter<T> filter(Filter<T> filter) {
		return new IterablePipeFilter<>(this, filter);
	}

	public default IterablePipeFilter<T> filter(Predicate<T> filter) {
		return new IterablePipeFilter<>(this, new FilterImpl<T>().emit(filter));
	}

	public default <U> IterablePipeFilter<T> filter(BiPredicate<T, U> filter, U u) {
		return filter(BiPredicatePlus.parsePredicate(filter, u));
	}

	public default IterablePipeFilter<T> skip(int times) {
		return filter(FilterImpl.skip(times));
	}

	public default IterablePipeFilter<T> subsample(int by) {
		return filter(FilterImpl.subsample(by));
	}

	public default IterablePipeLimit<T> limit(StopCriteriaImpl<T> stop) {
		return new IterablePipeLimit<>(this, stop);
	}

	public default IterablePipeLimit<T> limit(Predicate<T> stop) {
		return limit(new StopCriteriaImpl<T>().stop(stop));
	}

	public default <U> IterablePipeLimit<T> limit(BiPredicate<T, U> stop, U u) {
		return limit(BiPredicatePlus.parsePredicate(stop, u));
	}

	public default IterablePipeLimit<T> limit(int times) {
		return limit(StopCriteriaImpl.limit(times));
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

	public default <R> R apply(Function<Iterable<T>, R> transform) {
		return transform.apply(this);
	}

	public default <U, R> R apply(BiFunction<Iterable<T>, U, R> transform, U u) {
		return transform.apply(this, u);
	}

	public default Pipeline<IterablePipe<T>> pipe() {
		return Pipeline.newInstance(this);
	}

}
