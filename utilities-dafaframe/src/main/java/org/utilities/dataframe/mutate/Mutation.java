package org.utilities.dataframe.mutate;

import java.util.function.Function;

import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.dataframe.mutate.util.StringMutation;

public interface Mutation extends Function<DataEntry, DataValue> {

	String getName();

	// TODO move to StringSymbol
	public static MutationImpl split(String name, String x, String separator, int k) {
		return StringMutation.toString(name, x, value -> {
			String[] values = value.split(separator);
			return values.length < k ? values[k] : null;
		});
	}

}