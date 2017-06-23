package org.utilities.core.lang.iterable;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.util.function.PredicatePlus;

public class IterablePipeTracker<T> implements IterablePipe<T> {

	private Iterable<T> it;
	private Tracker tracker;

	public IterablePipeTracker(Iterable<T> it, Tracker tracker) {
		this.it = it;
		this.tracker = tracker;
	}

	public static <T> IterablePipeTracker<T> newInstance(Iterable<T> it, Tracker tracker) {
		return new IterablePipeTracker<>(it, tracker);
	}

	@Override
	public It<T> iterator() {
		return new It<>(it.iterator(), tracker);
	}

	public static class It<T> implements Iterator<T> {

		private Iterator<T> it;
		private Tracker tracker;
		private boolean started = false;
		private boolean ended = false;

		public It(Iterator<T> it, Tracker tracker) {
			this.it = it;
			this.tracker = tracker;
		}

		@Override
		public boolean hasNext() {
			if (!started) {
				notifyStart();
			}
			if (!ended && !it.hasNext()) {
				notifyEnd();
			}
			return !ended;
		}

		private void notifyStart() {
			tracker.start();
			started = true;
		}

		private void notifyEnd() {
			tracker.end();
			ended = true;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return it.next();
		}

	}

	public static interface Tracker {

		default void start() {

		}

		default void end() {

		}

		public static Tracker newInstance(Runnable start, Runnable end) {
			return new Tracker() {

				@Override
				public void start() {
					start.run();
				}

				@Override
				public void end() {
					end.run();
				}

			};
		}

		public static Tracker closer(Closeable closeable) {
			return new Tracker() {

				@Override
				public void end() {
					try {
						closeable.close();
					} catch (IOException e) {
						throw new QuietException(e);
					}
				}

			};
		}

		public static interface Predicate<T> extends Tracker, PredicatePlus<T> {

			public static <T> Tracker.Predicate<T> newInstance(Runnable start, java.util.function.Predicate<T> predicate) {
				return new Tracker.Predicate<T>() {

					@Override
					public void start() {
						start.run();
					}

					@Override
					public boolean test(T t) {
						return predicate.test(t);
					}
				};
			}

			public static <T> Tracker.Predicate<T> newInstance(Runnable start, java.util.function.Predicate<T> predicate, Runnable end) {
				return new Tracker.Predicate<T>() {

					@Override
					public void start() {
						start.run();
					}

					@Override
					public boolean test(T t) {
						return predicate.test(t);
					}

					@Override
					public void end() {
						end.run();
					}
				};

			}

			public static <T> Tracker.Predicate<T> newInstance(java.util.function.Predicate<T> predicate, Runnable end) {
				return new Tracker.Predicate<T>() {

					@Override
					public boolean test(T t) {
						return predicate.test(t);
					}

					@Override
					public void end() {
						end.run();
					}
				};

			}

			public static <T> Tracker.Predicate<T> tuples(int dim) {
				return new Tracker.Predicate<T>() {

					private Integer counter;

					@Override
					public void start() {
						counter = 0;
					}

					@Override
					public boolean test(T t) {
						if (++counter <= dim) {
							return true;
						} else {
							counter = 1;
							return false;
						}
					}

				};
			}

			public static <T> Tracker.Predicate<T> skip(int times) {
				return new Tracker.Predicate<T>() {

					private Integer counter;

					@Override
					public void start() {
						counter = 0;
					}

					@Override
					public boolean test(T t) {
						return times < ++counter;
					}

				};
			}

			public static <T> Tracker.Predicate<T> subsample(int by) {
				return new Tracker.Predicate<T>() {

					private Integer counter;

					@Override
					public void start() {
						counter = -1;
					}

					@Override
					public boolean test(T t) {
						return ++counter % by == 0;
					}

				};
			}

			public static <T> Tracker.Predicate<T> limit(int times) {
				return new Tracker.Predicate<T>() {

					private Integer counter;

					@Override
					public void start() {
						counter = 0;
					}

					@Override
					public boolean test(T t) {
						return ++counter <= times;
					}

				};
			}

			public static <T> Tracker.Predicate<T> interval(Function<T, Long> getTime, long timeWindow) {
				return new Tracker.Predicate<T>() {

					private Long fromTime;
					private Long toTime;

					@Override
					public void start() {
						fromTime = null;
						toTime = null;
					}

					@Override
					public boolean test(T evt) {
						long currentTime = getTime.apply(evt);
						if (fromTime == null) {
							move(currentTime);
						}
						boolean isIn = fromTime < currentTime && currentTime <= toTime;
						if (!isIn) {
							move(currentTime);
						}
						return isIn;
					}

					private void move(long currentTime) {
						fromTime = currentTime - currentTime % timeWindow;
						if (fromTime == currentTime) {
							fromTime -= timeWindow;
						}
						toTime = fromTime + timeWindow;
					}

				};
			}

		}

	}

}
