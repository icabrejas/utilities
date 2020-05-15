package org.utilities.core.lang.iterable.braid;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.utilities.core.lang.iterable.IterablePipe;

public class IPBraided<T> implements IterablePipe<T> {

	private int serialNumber;
	private IPBraider<T> selector;
	private List<? extends Iterable<T>> sources;

	public IPBraided(IPBraider<T> selector, List<? extends Iterable<T>> sources) {
		this.selector = selector;
		this.sources = sources;
	}

	public static <T> IPBraided<T> create(List<? extends Iterable<T>> sources) {
		IPBraider<T> selector = IPBraider.sequential(sources.size());
		return new IPBraided<>(selector, sources);
	}

	public static <T> IPBraided<T> create(Comparator<T> comparator, List<? extends Iterable<T>> sources) {
		IPBraider<T> selector = IPBraider.comparing(comparator);
		return new IPBraided<>(selector, sources);
	}

	@Override
	public Iterator<T> iterator() {
		List<Iterator<T>> iterators = sources.stream()
				.map(Iterable::iterator)
				.collect(Collectors.toList());
		return new ItBraided<>(serialNumber++, iterators, selector);
	}

}
