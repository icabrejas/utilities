package org.utilities.core.util.concurrent;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import org.utilities.core.lang.exception.QuietException;

public class ParallelRunner {

	private ExecutorService pool = UtilitiesThread.newThreadPool();

	public static ParallelRunner newInstance() {
		return new ParallelRunner();
	}

	public ParallelRunner execute(Runnable... tasks) {
		return execute(Arrays.asList(tasks));
	}

	public ParallelRunner execute(Iterable<Runnable> tasks) {
		for (Runnable task : tasks) {
			pool.execute(task);
		}
		return this;
	}

	public void shutdown(boolean wait) throws QuietException  {
		if (!pool.isShutdown()) {
			pool.shutdown();
		}
		if (wait) {
			UtilitiesThread.wait(pool);
		}
	}

}
