package org.utilities.core.lang.iterable.map;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.utilities.core.lang.iterable.track.IPTracker;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;

public class IPMapperImpl<T, R> extends IPTrackerImpl<T> implements IPMapper<T, R> {

	private BiFunction<Integer, T, R> map;

	public IPMapperImpl(BiFunction<Integer, T, R> map) {
		super();
		this.map = map;
	}

	public IPMapperImpl(Consumer<Integer> start, BiConsumer<Integer, T> next, Consumer<Integer> end,
			BiFunction<Integer, T, R> map) {
		super(start, next, end);
		this.map = map;
	}

	public IPMapperImpl(IPTracker<T> tracker, BiFunction<Integer, T, R> map) {
		super(tracker);
		this.map = map;
	}

	public IPMapperImpl(Function<T, R> map) {
		super();
		this.map = (serialNumber, t) -> map.apply(t);
	}

	public IPMapperImpl(Consumer<Integer> start, BiConsumer<Integer, T> next, Consumer<Integer> end,
			Function<T, R> map) {
		super(start, next, end);
		this.map = (serialNumber, t) -> map.apply(t);
	}

	public IPMapperImpl(IPTracker<T> tracker, Function<T, R> map) {
		super(tracker);
		this.map = (serialNumber, t) -> map.apply(t);
	}

	@Override
	public R map(int serialNumber, T t) {
		return map.apply(serialNumber, t);
	}

	public IPMapperImpl<T, R> map(BiFunction<Integer, T, R> map) {
		this.map = map;
		return this;
	}

}
