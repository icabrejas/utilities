package org.utilities.core.lang.iterable.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.tracker.IterablePipeTracked;
import org.utilities.core.util.concurrent.UtilitiesThread;

public class IterablePipeParallelMap<T, R> implements IterablePipe<R> {

	private IterablePipeTracked<T> it;
	private Mapper<T, R> mapper;
	private int nThreads;

	public IterablePipeParallelMap(Iterable<T> it, Mapper<T, R> mapper, int nThreads) {
		this.it = new IterablePipeTracked<>(it, mapper);
		this.mapper = mapper;
		this.nThreads = nThreads;
	}

	@Override
	public Iterator<R> iterator() {
		return new It<>(it.iterator(), mapper, nThreads);
	}

	// public static <T, R> List<R> parallelMap(List<T> it, Function<T, R>
	// mapper) {
	// ParallelCaller<R> caller = new ParallelCaller<>();
	// for (T t : it) {
	// caller.submit(() -> mapper.apply(t));
	// }
	// return caller.get();
	// }

	private static class It<T, R> implements Iterator<R> {

		private Iterator<T> it;
		private Mapper<T, R> mapper;
		private int nThreads;
		private ExecutorService pool;
		private List<Future<R>> tasks = new ArrayList<>();

		public It(Iterator<T> it, Mapper<T, R> mapper, int nThreads) {
			this.it = it;
			this.mapper = mapper;
			this.nThreads = nThreads;
			this.pool = UtilitiesThread.newThreadPool(nThreads);
		}

		@Override
		public boolean hasNext() {
			fill();
			return !tasks.isEmpty();
		}

		@Override
		public R next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			try {
				waitToNext();
				Future<R> task = tasks.remove(0);
				return task.get();
			} catch (Exception e) {
				throw new QuietException(e);
			}
		}

		private void waitToNext() {
			Future<R> task = tasks.get(0);
			while (!task.isDone()) {
				fill();
				UtilitiesThread.sleepQuietly(UtilitiesThread.DEFAULT_SLEEP_MILLIS);
			}
		}

		private void fill() {
			while (it.hasNext() && notFinishedTasks() < nThreads) {
				T t = it.next();
				Future<R> task = pool.submit(() -> mapper.map(t));
				tasks.add(task);
			}
		}

		private int notFinishedTasks() {
			int notFinishedTasks = 0;
			for (Future<R> task : tasks) {
				if (!task.isDone()) {
					notFinishedTasks++;
				}
			}
			return notFinishedTasks;
		}

	}

}
