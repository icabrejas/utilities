package org.utilities.core.lang.iterable.map;

import java.util.function.Consumer;
import java.util.function.Function;

import org.utilities.core.lang.iterable.tracker.TrackerImpl;

public class MapperImpl<T, R> extends TrackerImpl<T> implements Mapper<T, R> {

	private Function<T, R> map;

	public MapperImpl(Function<T, R> map) {
		this.map = map;
	}

	@Override
	public R map(T t) {
		return map.apply(t);
	}

	@Override
	public MapperImpl<T, R> start(Runnable start) {
		super.start(start);
		return this;
	}

	@Override
	public <U> MapperImpl<T, R> start(Consumer<U> start, U u) {
		super.start(start, u);
		return this;
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

	@Override
	public <U> MapperImpl<T, R> end(Consumer<U> end, U u) {
		super.end(end, u);
		return this;
	}

}
