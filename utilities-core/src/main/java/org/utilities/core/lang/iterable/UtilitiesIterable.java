package org.utilities.core.lang.iterable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.pair.Pair;

public class UtilitiesIterable {

	public static <T, U, R> IterablePipe<R> apply(Iterable<T> x, Iterable<U> y, BiFunction<T, U, R> func) {
		return IterablePipePair.newInstance(x, y)
				.map(BiFunctionPlus.parseFunction(func));
	}

	public static <T, U, R> IterablePipe<R> apply(Iterable<T> x, Function<T, R> func) {
		return IterablePipe.newInstance(x)
				.map(func);
	}

	public static <T> IterablePipe<T> filter(Iterable<T> it, Iterable<Boolean> sel) {
		return IterablePipePair.newInstance(sel, it)
				.filter(Pair::getX)
				.map(Pair::getY);
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

}
