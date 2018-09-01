package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.value.Value;

public class IsNull<S> implements Value<S, Boolean> {

	private Value<S, ?> x;

	public IsNull(Value<S, ?> x) {
		this.x = x;
	}

	@Override
	public Boolean apply(S store) {
		return x == null;
	}

}
