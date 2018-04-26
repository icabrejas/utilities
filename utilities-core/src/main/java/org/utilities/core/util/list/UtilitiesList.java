package org.utilities.core.util.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;

import org.utilities.core.lang.iterable.IterablePipeSequence;
import org.utilities.core.util.pair.PairImpl;

public class UtilitiesList {

	public static Integer[] toIntArray(List<Integer> headers) {
		return headers.toArray(new Integer[headers.size()]);
	}

	public static Long[] toLongArray(List<Long> headers) {
		return headers.toArray(new Long[headers.size()]);
	}

	public static Float[] toFloatArray(List<Float> headers) {
		return headers.toArray(new Float[headers.size()]);
	}

	public static Double[] toDoubleArray(List<Double> headers) {
		return headers.toArray(new Double[headers.size()]);
	}

	public static String[] toStringArray(List<String> headers) {
		return headers.toArray(new String[headers.size()]);
	}

	@SafeVarargs
	public static <T> List<T> concat(Collection<T>... data) {
		List<T> all = new ArrayList<>();
		for (Collection<T> x : data) {
			all.addAll(x);
		}
		return all;
	}

	public static <T> List<T> get(List<Integer> indexes, List<T> source) {
		return indexes.stream()
				.map(source::get)
				.collect(Collectors.toList());
	}

	public static <T extends Comparable<T>> List<Integer> order(List<T> sample) {
		return order(sample, Comparator.naturalOrder());
	}

	public static <T> List<Integer> order(List<T> sample, Comparator<? super T> sorter) {
		return IterablePipeSequence.from(0, sample.size() - 1, 1)
				.toList()
				.stream()
				.sorted((i, j) -> sorter.compare(sample.get(i), sample.get(j)))
				.collect(Collectors.toList());
	}

	public static <T extends Comparable<T>> List<T> quantiles(List<T> sample, int n) {
		return quantiles(sample, n, Comparator.naturalOrder());
	}

	public static <T> List<T> quantiles(List<T> sample, int n, Comparator<T> sorter) {
		List<Double> percentiles = IterablePipeSequence.from(1., n, 1.)
				.map(x -> x / (n + 1))
				.toList();
		return quantiles(sample, percentiles, sorter);
	}

	public static <T extends Comparable<T>> List<T> quantiles(List<T> sample, List<Double> percentiles) {
		return quantiles(sample, percentiles, Comparator.naturalOrder());
	}

	public static <T> List<T> quantiles(List<T> sample, List<Double> percentiles, Comparator<T> sorter) {
		List<Integer> index = order(sample, sorter);
		return percentiles.stream()
				.map(perc -> perc * sample.size())
				.map(Double::intValue)
				.map(index::get)
				.map(sample::get)
				.collect(Collectors.toList());
	}

	// FIXME use parallel??
	public static <T, U> U oneNN(T t, List<U> centroids, ToDoubleBiFunction<T, U> distance) {
		return centroids.stream()
				.map(centroid -> new PairImpl<>(distance.applyAsDouble(t, centroid), centroid))
				.min((c1, c2) -> Double.compare(c1.getX(), c2.getX()))
				.get()
				.getY();
	}

	// FIXME use parallel??
	public static <T, U> List<U> oneNN(List<T> sample, List<U> centroids, ToDoubleBiFunction<T, U> distance) {
		return sample.stream()
				.map(t -> oneNN(t, centroids, distance))
				.collect(Collectors.toList());
	}

	public static Double oneNN(Double x, List<Double> centroids) {
		return oneNN(x, centroids, UtilitiesList::doubleDistance);
	}

	public static List<Double> oneNN(List<Double> sample, List<Double> centroids) {
		return oneNN(sample, centroids, UtilitiesList::doubleDistance);
	}

	// FIXME move to correct class
	public static double doubleDistance(double a, double b) {
		return Math.abs(a - b);
	}

}
