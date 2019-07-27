package org.utilities.dataframe.select;

import org.utilities.core.util.function.PredicatePlus;

public interface Selection extends PredicatePlus<String> {

	@Override
	default Selection negate() {
		return PredicatePlus.super.negate()::test;
	}

}
