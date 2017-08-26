package org.utilities.core.lang.iterable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class UtilitiesIterable {

	public static <T, U, R> IterablePipe<R> apply(Iterable<T> x, Iterable<U> y, BiFunction<T, U, R> biFunction) {
		return new IterablePipePair<>(x, y)
				.map((Entry<T, U> pair) -> biFunction.apply(pair.getInfo(), pair.getContent()));
	}

	public static <T> IterablePipe<T> filter(Iterable<T> it, Iterable<Boolean> sel) {
		return IterablePipePair.newInstance(sel, it)
				.filter(Entry::getInfo)
				.map(Entry::getContent);
	}

	public static <T> List<T> toList(Iterable<T> it) {
		List<T> collector = new ArrayList<>();
		it.forEach(collector::add);
		return collector;
	}

	public static <T> Set<T> toSet(Iterable<T> it) {
		Set<T> collector = new HashSet<>();
		it.forEach(collector::add);
		return collector;
	}

	public static IterablePipe<Integer> sequence(int from, int to, int by) {
		return new IterablePipeSequence((double) from, (double) to, (double) by).map(Double::intValue);
	}

	public static IterablePipe<Integer> sequence(int from, int to) {
		return sequence(from, to, 1);
	}

	public static IterablePipe<Long> sequence(long from, long to, long by) {
		return new IterablePipeSequence((double) from, (double) to, (double) by).map(Double::longValue);
	}

	public static IterablePipe<Long> sequence(long from, long to) {
		return sequence(from, to, 1);
	}

}
