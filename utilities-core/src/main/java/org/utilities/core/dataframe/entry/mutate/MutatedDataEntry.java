package org.utilities.core.dataframe.entry.mutate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;

public class MutatedDataEntry implements DataEntry {

	private DataEntry entry;
	private Mutation mutation;

	public MutatedDataEntry(DataEntry entry, Mutation mutation) {
		this.entry = entry;
		this.mutation = mutation;
	}

	public static MutatedDataEntry newInstance(DataEntry entry, Mutation mutation) {
		return new MutatedDataEntry(entry, mutation);
	}

	public static MutatedDataEntry newInstance(DataEntry entry, String name, Function<DataEntry, DataValue> mutation) {
		return newInstance(entry, Mutation.dataEntryToDataValue(name, mutation));
	}

	@Override
	public Collection<String> keys() {
		Set<String> names = new HashSet<>(entry.keys());
		names.add(mutation.getName());
		return keys();
	}

	@Override
	public DataValue get(String name) {
		return name.equals(mutation.getName()) ? mutation.apply(entry) : entry.get(name);
	}
}
