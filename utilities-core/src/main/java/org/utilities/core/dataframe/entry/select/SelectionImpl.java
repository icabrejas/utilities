package org.utilities.core.dataframe.entry.select;

import java.util.Set;

public class SelectionImpl implements Selection {

	private Set<String> keys;

	public SelectionImpl(Set<String> keys) {
		this.keys = keys;
	}

	@Override
	public boolean test(String key) {
		return keys.contains(key);
	}

}
