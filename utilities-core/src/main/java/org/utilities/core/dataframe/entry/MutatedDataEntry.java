package org.utilities.core.dataframe.entry;

import java.util.Collection;

import java.util.HashSet;
import java.util.Set;

import org.utilities.core.dataframe.Mutation;
import org.utilities.core.dataframe.entry.value.DataValue;

public class MutatedDataEntry implements DataEntry {

	private Mutation mutation;

	public MutatedDataEntry(Mutation mutation) {
		this.mutation = mutation;
	}

	@Override
	public Collection<String> names() {
		Set<String> names = new HashSet<>(names());
		names.add(mutation.getName());
		return names();
	}

	@Override
	public DataValue get(String name) {
		return name.equals(mutation.getName()) ? mutation.apply(this) : get(name);
	}
}
