package org.utilities.dataframe.mutate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.dataentry.DataEntryImpl;
import org.utilities.dataframe.datavalue.DataValue;

public class MutatedDataEntry implements DataEntry {

	private DataEntry entry;
	private Mutation mutation;

	public MutatedDataEntry(DataEntry entry, Mutation mutation) {
		this.entry = entry;
		this.mutation = mutation;
	}

	public MutatedDataEntry(DataEntry entry, String name, Function<DataEntry, DataValue> mutation) {
		this(entry, MutationImpl.create(name, mutation));
	}

	public <U> MutatedDataEntry(DataEntry entry, String name, BiFunction<DataEntry, U, DataValue> mutation, U u) {
		this(entry, MutationImpl.create(name, mutation, u));
	}

	@Override
	public Collection<String> keys() {
		Set<String> names = new HashSet<>(entry.keys());
		names.add(mutation.getName());
		return names;
	}

	@Override
	public DataValue get(String name) {
		return name.equals(mutation.getName()) ? mutation.apply(entry) : entry.get(name);
	}

	@Override
	public String toString() {
		return new DataEntryImpl(this).toString();
	}

}
