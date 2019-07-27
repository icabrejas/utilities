package org.utilities.dataframe.mutate;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.DataValue;

public class MutationImpl implements Mutation {

	private String name;
	private Function<DataEntry, DataValue> mutation;

	private MutationImpl(String name, Function<DataEntry, DataValue> mutation) {
		this.name = name;
		this.mutation = mutation;
	}

	public static MutationImpl create(String name, Function<DataEntry, DataValue> mutation) {
		return new MutationImpl(name, mutation);
	}

	public static <U> MutationImpl create(String name, BiFunction<DataEntry, U, DataValue> mutation, U u) {
		return new MutationImpl(name, BiFunctionPlus.parseFunction(mutation, u));
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
