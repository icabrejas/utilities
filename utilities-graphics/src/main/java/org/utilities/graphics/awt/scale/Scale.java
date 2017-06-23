package org.utilities.graphics.awt.scale;


import java.util.function.Function;

import org.utilities.core.util.function.FunctionPlus;

public interface Scale<I, O> {

	public O transform(I input);

	public I invert(O output);

	public static <I, O> Scale<I, O> newInstance(Function<I, O> transform, Function<O, I> invert) {
		return new Scale<I, O>() {

			@Override
			public O transform(I input) {
				return transform.apply(input);
			}

			@Override
			public I invert(O output) {
				return invert.apply(output);
			}
		};
	}

	public static <I, O> Scale<I, O> newInstance(Range<I> input, Range<O> output) {
		Function<I, O> transform = FunctionPlus.compose(output::get, input::project);
		Function<O, I> invert = FunctionPlus.compose(input::get, output::project);
		return newInstance(transform, invert);
	}

}
