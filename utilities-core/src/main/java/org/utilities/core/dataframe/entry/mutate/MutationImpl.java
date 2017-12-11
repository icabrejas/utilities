package org.utilities.core.dataframe.entry.mutate;

import java.util.function.Function;

import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;

public class MutationImpl  implements Mutation {

	private String name;
	private Function<DataEntry, DataValue> mutation;

	public MutationImpl(String name, Function<DataEntry, DataValue> mutation) {
		this.name = name;
		this.mutation = mutation;
	}

	@Override
	public DataValue apply(DataEntry entry) {
		return mutation.apply(entry);
	}

	@Override
	public String getName() {
		return name;
	}
}
