package org.utilities.core.dataframe;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.MutatedDataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.util.function.BiFunctionPlus;

public interface Mutation extends Function<DataEntry, DataValue> {

	String getName();

	default DataEntry mutate(DataEntry entry) {
		return new MutatedDataEntry(this);
	}

	public static Mutation.Basic newInstance(String label) {
		return new Basic(label);
	}

	public static class Basic implements Mutation {

		private String name;
		private Function<DataEntry, DataValue> mutation;

		public Basic(String name) {
			this.name = name;
			this.mutation = BiFunctionPlus.parseFunction(DataEntry::get, name);
		}

		public Mutation.Basic mutation(Function<DataEntry, DataValue> mutation) {
			this.mutation = mutation;
			return this;
		}

		public Mutation.Basic mutation(String x, Function<DataValue, DataValue> mutation) {
			this.mutation = entry -> mutation.apply(entry.get(x));
			return this;
		}

		public Mutation.Basic mutation(String x, String y, BiFunction<DataValue, DataValue, DataValue> mutation) {
			this.mutation = entry -> mutation.apply(entry.get(x), entry.get(y));
			return this;
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

}