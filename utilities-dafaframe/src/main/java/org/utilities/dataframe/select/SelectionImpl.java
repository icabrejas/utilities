package org.utilities.dataframe.select;

import java.util.Arrays;
import java.util.Collection;

public class SelectionImpl implements Selection {

	private Collection<String> names;

	public SelectionImpl(Collection<String> names) {
		this.names = names;
	}

	public SelectionImpl(String... names) {
		this(Arrays.asList(names));
	}

	@Override
	public boolean test(String key) {
		return names.contains(key);
	}

}
