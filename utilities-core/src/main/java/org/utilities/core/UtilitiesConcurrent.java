package org.utilities.core;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.util.concurrent.Interval;
import org.utilities.core.util.concurrent.ParallelCaller;
import org.utilities.core.util.concurrent.ParallelRunner;
import org.utilities.core.util.concurrent.TrackedCallable;
import org.utilities.core.util.concurrent.TrackedFuture;

public class UtilitiesConcurrent {

	public static final int DEFAULT_SLEEP_MILLIS = 60;

	public static ExecutorService newThreadPool() {
		return newThreadPool(nThreads());
	}

	public static int nThreads() {
		return Math.max(1, 4 * UtilitiesRuntime.availableProcessors());
	}

	public static ExecutorService newThreadPool(int nThreads) {
		return Executors.newFixedThreadPool(nThreads);
	}

	public static void run(Runnable runnable) {
		new Thread(runnable).start();
	}

	public static void runParallel(Iterable<Runnable> tasks, boolean wait) {
		ParallelRunner runner = new ParallelRunner();
		runner.execute(tasks);
		runner.shutdown(wait);
	}

	public static <T> List<T> callParallel(Iterable<? extends Callable<T>> tasks) {
		ParallelCaller<T> caller = new ParallelCaller<>();
		caller.submitAll(tasks);
		return caller.get();
	}

	public static <T> List<T> get(List<Future<T>> results) {
		return results.stream()
				.map(UtilitiesConcurrent::getQuietly)
				.collect(Collectors.toList());
	}

	public static <T> T getQuietly(Future<T> result) throws QuietException {
		try {
			return result.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new QuietException(e);
		}
	}

	public static void waitQuietly(ExecutorService pool) throws QuietException {
		waitQuietly(pool, DEFAULT_SLEEP_MILLIS);
	}

	public static void waitQuietly(ExecutorService pool, long sleepMillis) throws QuietException {
		if (!pool.isShutdown()) {
			pool.shutdown();
		}
		waitQuietly(pool::isTerminated, sleepMillis);
	}

	public static void waitQuietly(Supplier<Boolean> semaphore) throws QuietException {
		while (!semaphore.get()) {
			UtilitiesConcurrent.sleepQuietly(DEFAULT_SLEEP_MILLIS);
		}
	}

	public static void waitQuietly(Supplier<Boolean> semaphore, long millis) throws QuietException {
		while (!semaphore.get()) {
			UtilitiesConcurrent.sleepQuietly(millis);
		}
	}

	public static void sleepQuietly(long millis) throws QuietException {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new QuietException(e);
		}
	}

	public static Interval setInterval(Runnable runnable) {
		return setInterval(runnable, DEFAULT_SLEEP_MILLIS);
	}

	public static Interval setInterval(Runnable runnable, long millis) {
		Interval interval = new Interval(runnable, millis);
		interval.start();
		return interval;
	}

	public static <V> TrackedFuture<V> submitAndTrack(ExecutorService pool, Callable<V> callable) {
		TrackedCallable<V> trackedCallable = new TrackedCallable<V>(callable);
		Future<V> future = pool.submit(trackedCallable);
		return new TrackedFuture<>(future, trackedCallable);
	}

}
