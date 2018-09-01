package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.value.Value;

public class IsNotNull<S> implements Value<S, Boolean> {

	private Value<S, ?> x;

	public IsNotNull(Value<S, ?> x) {
		this.x = x;
	}

	@Override
	public Boolean apply(S store) {
		return x != null;
	}

}
