package org.utilities.core.util.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.utilities.core.UtilitiesConcurrent;
import org.utilities.core.lang.exception.QuietException;

public class ParallelCaller<T> implements Iterable<T> {

	private ExecutorService pool = UtilitiesConcurrent.newThreadPool();
	private List<Future<T>> results = new ArrayList<>();

	public static <T> ParallelCaller<T> from() {
		return new ParallelCaller<T>();
	}

	public ParallelCaller<T> submit(Callable<T> task) {
		results.add(pool.submit(task));
		return this;
	}

	@SuppressWarnings("unchecked")
	public ParallelCaller<T> submitAll(Callable<T>... tasks) {
		return submitAll(Arrays.asList(tasks));
	}

	public ParallelCaller<T> submitAll(Iterable<? extends Callable<T>> tasks) {
		for (Callable<T> task : tasks) {
			submit(task);
		}
		return this;
	}

	public void shutdown(boolean wait) throws QuietException {
		if (!pool.isShutdown()) {
			pool.shutdown();
		}
		if (wait) {
			UtilitiesConcurrent.waitQuietly(pool);
		}
	}

	public List<T> get() throws QuietException {
		shutdown(true);
		return UtilitiesConcurrent.get(results);
	}

	@Override
	public Iterator<T> iterator() {
		return get().iterator();
	}

}
