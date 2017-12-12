package org.utilities.core.lang.iterable.map;

import java.util.function.Function;

import org.utilities.core.lang.iterable.tracker.TrackerImpl;

public class MapperImpl<T, R> extends TrackerImpl<T> implements Mapper<T, R> {

	private Function<T, R> map;

	@Override
	public MapperImpl<T, R> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public R map(T t) {
		return map.apply(t);
	}

	public MapperImpl<T, R> map(Function<T, R> map) {
		this.map = map;
		return this;
	}

	@Override
	public MapperImpl<T, R> end(Runnable end) {
		super.end(end);
		return this;
	}

}
