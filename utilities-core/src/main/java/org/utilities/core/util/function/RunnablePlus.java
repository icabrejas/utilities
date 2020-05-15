package org.utilities.core.util.function;

import org.utilities.core.lang.exception.QuietException;

public interface RunnablePlus extends Runnable {

	public static RunnablePlus dummy() {
		return () -> {
		};
	}

	static RunnablePlus create(Runnable runnable) {
		return runnable::run;
	}

	static <T> ConsumerPlus<T> parseConsumer(Runnable runnable) {
		return t -> runnable.run();
	}

	default RunnablePlus andThen(Runnable runnable) {
		return () -> {
			this.run();
			runnable.run();
		};
	}

	default RunnablePlus compose(Runnable runnable) {
		return () -> {
			runnable.run();
			this.run();
		};
	}

	default <T> ConsumerPlus<T> parseConsumer() {
		return RunnablePlus.parseConsumer(this);
	}

	static RunnablePlus parseQuiet(RunnablePlus.Noisy noisy) {
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

		public static void tryToRun(RunnablePlus.Noisy runnable, int maxTimes) throws QuietException {
			int fails = 0;
			boolean executed = false;
			while (!executed) {
				try {
					runnable.run();
					executed = true;
				} catch (Exception e) {
					if (++fails == maxTimes) {
						throw new QuietException(e);
					}
				}
			}
		}

	}

}
