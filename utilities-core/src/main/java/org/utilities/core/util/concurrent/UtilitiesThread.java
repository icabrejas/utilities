package org.utilities.core.util.concurrent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.runtime.UtilitiesRuntime;
import org.utilities.core.util.function.FunctionPlus;

public class UtilitiesThread {

	public static ExecutorService newThreadPool() {
		return newThreadPool(Math.max(1, UtilitiesRuntime.availableProcessors() - 1));
	}

	public static ExecutorService newThreadPool(int nThreads) {
		return Executors.newFixedThreadPool(nThreads);
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
		return IterablePipe.newInstance(results)
				.map(FunctionPlus.parseQuiet(Future::get))
				.toList();
	}

	public static <T> T get(Future<T> result) throws InterruptedException, ExecutionException {
		return result.get();
	}

	public static void wait(ExecutorService pool) throws QuietException {
		wait(pool, 100);
	}

	public static void wait(ExecutorService pool, long sleepMillis) throws QuietException {
		if (!pool.isShutdown()) {
			pool.shutdown();
		}
		wait(pool::isTerminated, sleepMillis);
	}

	public static void wait(Supplier<Boolean> semaphore) throws QuietException {
		while (!semaphore.get()) {
			UtilitiesThread.sleepQuietly(100);
		}
	}

	public static void wait(Supplier<Boolean> semaphore, long sleepMillis) throws QuietException {
		while (!semaphore.get()) {
			UtilitiesThread.sleepQuietly(sleepMillis);
		}
	}

	public static void sleepQuietly(long millis) throws QuietException {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new QuietException(e);
		}
	}

	public static void setTimeout(Runnable runnable, long timeoutPeriod) {
		new Thread(() -> {
			while (true) {
				UtilitiesThread.sleepQuietly(timeoutPeriod);
				runnable.run();
			}
		}).start();
	}

	public static void run(Runnable runnable) {
		new Thread(runnable).start();
	}

}
