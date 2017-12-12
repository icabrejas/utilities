package org.utilities.core.dataframe.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.utilities.core.dataframe.entry.bind.BindedDataEntry;
import org.utilities.core.dataframe.entry.mutate.MutatedDataEntry;
import org.utilities.core.dataframe.entry.mutate.Mutation;
import org.utilities.core.dataframe.entry.remove.RemovedDataEntry;
import org.utilities.core.dataframe.entry.select.SelectedDataEntry;
import org.utilities.core.dataframe.entry.select.Selection;
import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.dataframe.entry.value.StringDataValue;

public interface DataEntry {

	Collection<String> keys();

	DataValue get(String key);

	default Collection<DataValue> get(Collection<String> keys) {
		List<DataValue> values = new ArrayList<>();
		for (String key : keys) {
			values.add(get(key));
		}
		return values;
	}

	default String getString(String name) {
		return get(name).stringValue();
	}

	default Integer getInt(String name) {
		return get(name).intValue();
	}

	default Long getLong(String name) {
		return get(name).longValue();
	}

	default Float getFloat(String name) {
		return get(name).floatValue();
	}

	default Double getDouble(String name) {
		return get(name).doubleValue();
	}

	default Date getDate(String name) {
		return get(name).dateValue();
	}

	default Collection<DataValue> values() {
		return get(keys());
	}

	default boolean contains(String name) {
		return keys().contains(name);
	}

	default boolean containsKeys(Collection<String> keys) {
		return keys().containsAll(keys);
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

	default DataEntry select(Selection selection) {
		return SelectedDataEntry.newInstance(this, selection);
	}

	default DataEntry select(Collection<String> names) {
		return select(Selection.newInstance(names));
	}

	default DataEntry select(String... names) {
		return select(Arrays.asList(names));
	}

	default DataEntry remove(Selection selection) {
		return RemovedDataEntry.newInstance(this, selection);
	}

	default DataEntry remove(Collection<String> names) {
		return remove(Selection.newInstance(names));
	}

	default DataEntry remove(String... names) {
		return remove(Arrays.asList(names));
	}

	default <T> DataEntry mutate(Mutation mutation) {
		return MutatedDataEntry.newInstance(this, mutation);
	}

	default <T> DataEntry mutate(String name, Function<DataEntry, DataValue> mutation) {
		return MutatedDataEntry.newInstance(this, name, mutation);
	}

	default List<DataEntry> gather(String key, String value, String... names) {
		return gather(key, value, Arrays.asList(names));
	}

	default List<DataEntry> gather(String key, String value, Collection<String> names) {
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

	default DataEntry separate(String key, String separator, String... names) {
		return separate(key, separator, Arrays.asList(names));
	}

	// TODO: do on the fly
	default DataEntry separate(String key, String separator, Collection<String> names) {
		String[] values = getString(key).split(separator);
		DataEntry entry = null;
		int i = 0;
		for (String name : names) {
			StringDataValue value = values.length < i ? new StringDataValue(values[i]) : null;
			entry = this.mutate(name, e -> value);
			i++;
		}
		return entry;
	}

}
