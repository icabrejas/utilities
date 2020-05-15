package org.utilities.core.lang.iterable.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utilities.core.UtilitiesConcurrent;
import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.track.ItTracked;

public class ItMappedParallelly<T, R> implements Iterator<R> {

	private static Logger LOGGER = LoggerFactory.getLogger(ItMappedParallelly.class);

	private int serialNumber;
	private Iterator<T> it;
	private IPMapper<T, R> mapper;
	private int queueSize;
	private ExecutorService pool;
	private List<Future<R>> queue = new ArrayList<>();

	public ItMappedParallelly(int serialNumber, Iterator<T> it, IPMapper<T, R> mapper, int size) {
		this(serialNumber, it, mapper, size, size);
	}

	public ItMappedParallelly(int serialNumber, Iterator<T> it, IPMapper<T, R> mapper, int poolSize, int queueSize) {
		this.serialNumber = serialNumber;
		this.it = new ItTracked<>(serialNumber, it, mapper);
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
			Future<R> task = UtilitiesConcurrent.submitAndTrack(pool, () -> mapper.map(serialNumber, t));
			queue.add(task);
		}
	}

}