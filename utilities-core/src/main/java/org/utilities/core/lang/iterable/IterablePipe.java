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
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.utilities.core.time.TicToc;
import org.utilities.core.util.function.BiConsumerPlus;
import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.function.BiPredicatePlus;
import org.utilities.core.util.function.FunctionPlus;
import org.utilities.core.util.function.Pipeline;

public interface IterablePipe<T> extends Iterable<T> {

	public static <T> IterablePipe<T> newInstance(Supplier<? extends Iterator<T>> it) {
		return it::get;
	}

	public static <T, U> IterablePipe<U> newInstance(Function<T, ? extends Iterator<U>> it, T t) {
		return newInstance(FunctionPlus.parseSupplier(it, t));
	}

	@SuppressWarnings("unchecked")
	public static <T> IterablePipe<T> newInstance(Class<T> type) {
		return IterablePipe.newInstance();
	}

	@SuppressWarnings("unchecked")
	public static <T> IterablePipe<T> newInstance(T... t) {
		return IterablePipe.newInstance(Arrays.asList(t));
	}

	public static <T> IterablePipe<T> newInstance(Iterable<T> it) {
		return it::iterator;
	}

	public default <R> IterablePipeMap<T, R> map(Function<T, R> mapper) {
		return new IterablePipeMap<>(this, mapper);
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

	public default <R> IterablePipeFlat<R> flatMap(Function<T, ? extends Iterable<R>> mapper) {
		return new IterablePipeFlat<>(map(mapper));
	}

	public default <U, R> IterablePipeFlat<R> flatMap(BiFunction<T, U, Iterable<R>> mapper, U u) {
		return flatMap(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipeFlat<R> flatMap(BiFunction<T, U, Iterable<R>> mapper, Supplier<? extends U> u) {
		return flatMap(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default <U, R> IterablePipeFlat<R> flatMap(BiFunction<T, U, Iterable<R>> mapper, Function<T, ? extends U> u) {
		return flatMap(BiFunctionPlus.parseFunction(mapper, u));
	}

	public default IterablePipeFlat<T> bind(Iterable<T> it) {
		return new IterablePipeFlat<>(Arrays.asList(this, it));
	}

	public default IterablePipeBatch<T> batch(IterablePipeBatch.Semaphore<T> semaphore) {
		return new IterablePipeBatch<>(this, semaphore);
	}

	public default IterablePipeBatch<T> batch(Predicate<T> semaphore) {
		return batch(IterablePipeBatch.Semaphore.newInstance(semaphore));
	}

	public default <U> IterablePipeBatch<T> batch(BiPredicate<T, U> semaphore, U u) {
		return batch(IterablePipeBatch.Semaphore.newInstance(semaphore, u));
	}

	public default IterablePipeBatch<T> batch(IterablePipeTracker.Tracker tracker, Predicate<T> semaphore) {
		return new IterablePipeTracker<>(this, tracker).batch((Predicate<T>) semaphore);
	}

	// FIXME validate if the correct method is called.
	public default IterablePipeBatch<T> batch(IterablePipeTracker.Tracker.Predicate<T> semaphore) {
		return batch(semaphore, semaphore);
	}

	public default IterablePipeBatch<T> tuples(int dim) {
		return batch(IterablePipeTracker.Tracker.Predicate.tuples(dim));
	}

	public default IterablePipeObserver<T> catchAndRelease(Consumer<T> observer) {
		return new IterablePipeObserver<>(this, observer);
	}

	public default <U> IterablePipeObserver<T> catchAndRelease(BiConsumer<T, U> observer, U u) {
		return catchAndRelease(BiConsumerPlus.parseConsumer(observer, u));
	}

	public default IterablePipeObserver<T> println() {
		return catchAndRelease(System.out::println);
	}

	public default IterablePipeObserver<T> ticToc(String pattern) {
		return catchAndRelease(IterablePipeObserver.ticTocObserver(pattern));
	}

	public default IterablePipeObserver<T> ticToc() {
		return ticToc(TicToc.DEFAULT_PATTERN);
	}

	public default IterablePipe<T> log(long frequency) {
		return catchAndRelease(IterablePipeObserver.logObserver(frequency));
	}

	public default IterablePipeFilter<T> filter(Predicate<T> filter) {
		return new IterablePipeFilter<>(this, filter);
	}

	public default <U> IterablePipeFilter<T> filter(BiPredicate<T, U> filter, U u) {
		return filter(BiPredicatePlus.parsePredicate(filter, u));
	}

	public default IterablePipeFilter<T> filter(IterablePipeTracker.Tracker tracker, Predicate<T> filter) {
		return new IterablePipeTracker<>(this, tracker).filter((Predicate<T>) filter);
	}

	// FIXME validate if the correct method is called.
	public default IterablePipeFilter<T> filter(IterablePipeTracker.Tracker.Predicate<T> filter) {
		return filter(filter, filter);
	}

	public default IterablePipeFilter<T> skip(int times) {
		return filter(IterablePipeTracker.Tracker.Predicate.skip(times));
	}

	public default IterablePipeLimit<T> limit(Predicate<T> stopCriteria) {
		return new IterablePipeLimit<>(this, stopCriteria);
	}

	public default <U> IterablePipeLimit<T> limit(BiPredicate<T, U> stopCriteria, U u) {
		return limit(BiPredicatePlus.parsePredicate(stopCriteria, u));
	}

	public default IterablePipeLimit<T> limit(IterablePipeTracker.Tracker resettable, Predicate<T> stopCriteria) {
		return new IterablePipeTracker<>(this, resettable).limit((Predicate<T>) stopCriteria);
	}

	// FIXME validate if the correct method is called.
	public default IterablePipeLimit<T> limit(IterablePipeTracker.Tracker.Predicate<T> stopCriteria) {
		return limit(stopCriteria, stopCriteria);
	}

	public default IterablePipeLimit<T> limit(int times) {
		return limit(IterablePipeTracker.Tracker.Predicate.limit(times));
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

	public default IterablePipeCache<T> cache(int bufferSize) {
		return new IterablePipeCache<>(this, bufferSize);
	}

}
