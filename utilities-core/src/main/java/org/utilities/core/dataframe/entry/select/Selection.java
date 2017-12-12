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

	public static SelectionImpl newInstance(Set<String> keys) {
		return new SelectionImpl(keys);
	}

	public static SelectionImpl newInstance(Collection<String> keys) {
		return newInstance(new HashSet<>(keys));
	}

	public static SelectionImpl newInstance(String... keys) {
		return newInstance(Arrays.asList(keys));
	}

}
