package org.utilities.core.util.function;

import org.utilities.core.lang.exception.QuietException;

public interface RunnablePlus extends Runnable {

	public static RunnablePlus dummy() {
		return () -> {
		};
	}

	static RunnablePlus parseQuiet(Noisy noisy) {
		return () -> {
			try {
				noisy.run();
			} catch (Exception e) {
				throw new QuietException(e);
			}
		};
	}

	public static interface Noisy {

		void run() throws Exception;

		public static <T, R> void tryToRun(RunnablePlus.Noisy runnable, T t, int maxTimes) {
			int fails = 0;
			boolean executed = false;
			while (!executed) {
				try {
					runnable.run();
					executed = true;
				} catch (Exception e) {
					if (++fails == 3) {
						throw new QuietException(e);
					}
				}
			}
		}

	}
}
