package org.utilities.symbolicmath.logical;

import org.utilities.symbolicmath.operator.UnaryOperator;
import org.utilities.symbolicmath.value.Value;

public class Not<S> extends UnaryOperator<S, Boolean, Boolean> {

	public Not(Value<S, Boolean> a) {
		super(x -> !x, a);
	}

}
