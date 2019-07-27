package org.utilities.core.lang.iterable.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.tracker.IterablePipeTracked;
import org.utilities.core.util.concurrent.UtilitiesConcurrent;

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

	private static class It<T, R> implements Iterator<R> {

		private static Logger LOGGER = LoggerFactory.getLogger(IterablePipeParallelMap.It.class);

		private Iterator<T> it;
		private Mapper<T, R> mapper;
		private int queueSize;
		private ExecutorService pool;
		private List<Future<R>> queue = new ArrayList<>();

		public It(Iterator<T> it, Mapper<T, R> mapper, int size) {
			this(it, mapper, size, size);
		}

		public It(Iterator<T> it, Mapper<T, R> mapper, int poolSize, int queueSize) {
			this.it = it;
			this.mapper = mapper;
			this.queueSize = queueSize;
			this.pool = UtilitiesConcurrent.newThreadPool(poolSize);
		}

		@Override
		public boolean hasNext() {
			fill();
			if (!queue.isEmpty()) {
				return true;
			} else {
				if (!pool.isShutdown()) {
					pool.shutdown();
				}
				return false;
			}
		}

		@Override
		public R next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			try {
				waitToNext();
				Future<R> task = queue.remove(0);
				LOGGER.info(task.toString());
				return task.get();
			} catch (Exception e) {
				throw new QuietException(e);
			}
		}

		private void waitToNext() {
			UtilitiesConcurrent.waitQuietly(queue.get(0)::isDone);
		}

		private void fill() {
			while (it.hasNext() && queue.size() < queueSize) {
				T t = it.next();
				Future<R> task = UtilitiesConcurrent.submitAndTrack(pool, () -> mapper.map(t));
				queue.add(task);
			}
		}

	}

}
