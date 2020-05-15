package org.utilities.core.lang.iterable.braid;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;
import org.utilities.core.util.lambda.LambdaInt;

public interface IPBraider<T> extends IPTracker<Map.Entry<Integer, T>>, Comparator<Map.Entry<Integer, T>> {

	public static <T> IPBraiderImpl<T> concurrent(Comparator<Entry<Integer, T>> selector) {
		return new IPBraiderImpl<>(IPTracker.concurrent(), selector);
	}

	public static <T> IPBraider<T> sequential(int lenght) {
		LambdaInt ref = new LambdaInt();
		Comparator<Map.Entry<Integer, T>> selector = (entry1, entry2) -> {
			int dist1 = Math.abs(entry1.getKey() - ref.get());
			int dist2 = Math.abs(entry2.getKey() - ref.get());
			return Integer.compare(dist1, dist2);
		};
		IPTrackerImpl<Map.Entry<Integer, T>> tracker = IPTracker.concurrent();
		tracker.start(() -> ref.set(0));
		tracker.next(t -> ref.set(ref.increment() % lenght));
		return new IPBraiderImpl<>(tracker, selector);
	}

	public static <T> IPBraider<T> comparing(Comparator<T> comparator) {
		Comparator<Map.Entry<Integer, T>> selector = Comparator.comparing(Map.Entry::getValue, comparator);
		selector = selector.thenComparing(Map.Entry::getKey);
		return new IPBraiderImpl<>(selector);
	}

	public static <T extends Comparable<T>> IPBraider<T> naturalOrder() {
		Comparator<T> comparator = Comparator.naturalOrder();
		return comparing(comparator);
	}

}
