package org.utilities.core.util.function;

import java.util.Objects;
import java.util.function.Consumer;

import org.utilities.core.lang.exception.QuietException;

public interface ConsumerPlus<T> extends Consumer<T> {

	public static <T> ConsumerPlus<T> dummy(Class<T> type) {
		return dummy();
	}

	public static <T> ConsumerPlus<T> dummy() {
		return (t) -> {
		};
	}

	public static <T> ConsumerPlus<T> create(Consumer<T> consumer) {
		return consumer::accept;
	}

	public static <T> RunnablePlus parseRunnable(Consumer<T> consumer, T t) {
		return () -> consumer.accept(t);
	}

	default RunnablePlus parseRunnable(T t) {
		return ConsumerPlus.parseRunnable(this, t);
	}

	public static <T> FunctionPlus<T, T> parseFunction(Consumer<T> consumer) {
		return t -> {
			consumer.accept(t);
			return t;
		};
	}

	default ConsumerPlus<T> andThen(Consumer<? super T> after) {
		return Consumer.super.andThen(after)::accept;
	}

	default ConsumerPlus<T> compose(Consumer<? super T> before) {
		Objects.requireNonNull(before);
		return (T t) -> {
			before.accept(t);
			accept(t);
		};
	}

	default FunctionPlus<T, T> parseFunction() {
		return ConsumerPlus.parseFunction(this);
	}

	static <T> ConsumerPlus<T> parseQuiet(ConsumerPlus.Noisy<T> noisy) {
		return t -> {
			try {
				noisy.accept(t);
			} catch (Exception e) {
				throw new QuietException(e);
			}
		};
	}

	public static interface Noisy<T> {

		void accept(T t) throws Exception;

		public static <T> void tryToAccept(ConsumerPlus.Noisy<T> consumer, T t, int maxTimes) throws QuietException {
			int fails = 0;
			boolean executed = false;
			while (!executed) {
				try {
					consumer.accept(t);
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
