package org.utilities.core.dataframe.entry;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.utilities.core.dataframe.Mutation;
import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.dataframe.entry.value.StringDataValue;

public interface DataEntry {

	Collection<String> names();

	DataValue get(String name);

	default Collection<DataValue> values() {
		return values(names());
	}

	default Collection<DataValue> values(Collection<String> names) {
		return names.stream()
				.map(this::get)
				.collect(Collectors.toList());
	}

	default boolean contains(String name) {
		return names().contains(name);
	}

	default boolean containsKeys(Collection<String> names) {
		return names().containsAll(names);
	}

	// TODO create Binder class
	public static DataEntry bind(String prefixA, DataEntry entryA, String prefixB, DataEntry entryB) {
		Map<String, DataEntry> entries = new HashMap<String, DataEntry>();
		entries.put(prefixA, entryA);
		entries.put(prefixB, entryB);
		return bind(entries);
	}

	public static DataEntry bind(Map<String, DataEntry> entries) {
		return new BindedDataEntry(entries);
	}

	// TODO create Selector class
	default DataEntry select(Collection<String> names) {
		return new SelectedDataEntry(this, names);
	}

	default DataEntry select(String... names) {
		return select(Arrays.asList(names));
	}

	// TODO create Remover class
	default DataEntry remove(Collection<String> names) {
		return new RemovedDataEntry(this, names);
	}

	default DataEntry remove(String... names) {
		return remove(Arrays.asList(names));
	}

	default <T> DataEntry mutate(Mutation mutation) {
		return mutation.mutate(this);
	}

	default <T> DataEntry mutate(String name, Function<DataEntry, DataValue> func) {
		return Mutation.newInstance(name)
				.mutation(func)
				.mutate(this);
	}

	default <T> DataEntry mutate(String name, String x, Function<DataValue, DataValue> func) {
		return Mutation.newInstance(name)
				.mutation(x, func)
				.mutate(this);
	}

	default <T> DataEntry mutate(String name, String x, String y, BiFunction<DataValue, DataValue, DataValue> func) {
		return Mutation.newInstance(name)
				.mutation(x, y, func)
				.mutate(this);
	}

	default List<? extends DataEntry> gather(String key, String value, String... names) {
		return gather(key, value, Arrays.asList(names));
	}

	default List<? extends DataEntry> gather(String key, String value, Collection<String> names) {
		return names.stream()
				.map(name -> gather(key, value, name))
				.map(entry -> entry.remove(names))
				.collect(Collectors.toList());
	}

	default DataEntry gather(String key, String value, String name) {
		return this.mutate(key, entry -> new StringDataValue(name))
				.mutate(value, entry -> entry.get(name))
				.remove(name);
	}

}
