package org.utilities.core.util.concurrent;

public class Interval {

	private boolean stop = true;
	private Runnable task;

	public Interval(Runnable runnable, long delay) {
		this.task = () -> {
			while (!stop) {
				runnable.run();
				UtilitiesConcurrent.sleepQuietly(delay);
			}
		};
	}

	public void start() {
		if (stop) {
			stop = false;
			new Thread(task).start();
		}
	}

	public void stop() {
		if (!stop) {
			stop = true;
		}
	}

}
