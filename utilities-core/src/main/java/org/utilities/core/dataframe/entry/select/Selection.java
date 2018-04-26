package org.utilities.core.dataframe.entry.select;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.utilities.core.util.function.PredicatePlus;

public interface Selection extends PredicatePlus<String> {

	@Override
	default Selection negate() {
		return PredicatePlus.super.negate()::test;
	}

	public static SelectionImpl from(Set<String> keys) {
		return new SelectionImpl(keys);
	}

	public static SelectionImpl from(Collection<String> keys) {
		return from(new HashSet<>(keys));
	}

	public static SelectionImpl from(String... keys) {
		return from(Arrays.asList(keys));
	}

}
