package org.utilities.dataframe.dataentry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.utilities.dataframe.bind.BindedDataEntry;
import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.dataframe.datavalue.StringDataValue;
import org.utilities.dataframe.mutate.MutatedDataEntry;
import org.utilities.dataframe.mutate.Mutation;
import org.utilities.dataframe.remove.RemovedDataEntry;
import org.utilities.dataframe.rename.Rename;
import org.utilities.dataframe.rename.RenamedDataEntry;
import org.utilities.dataframe.select.SelectedDataEntry;
import org.utilities.dataframe.select.Selection;
import org.utilities.dataframe.select.SelectionImpl;

public interface DataEntry {

	Collection<String> keys();

	DataValue get(String key);

	default Collection<DataValue> values() {
		return get(keys());
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

	default Boolean getBoolean(String name) {
		return get(name).booleanValue();
	}

	default List<DataValue> get(Collection<String> keys) {
		List<DataValue> values = new ArrayList<>();
		for (String key : keys) {
			values.add(get(key));
		}
		return values;
	}

	default boolean contains(String name) {
		return keys().contains(name);
	}

	default boolean containsKeys(Collection<String> keys) {
		return keys().containsAll(keys);
	}

	public static DataEntry bindColumns(List<DataEntry> dataEntries, List<String> prefixes) {
		return new BindedDataEntry(dataEntries, prefixes);
	}

	public static DataEntry bindColumns(DataEntry entryA, String prefixA, DataEntry entryB, String prefixB) {
		return new BindedDataEntry(entryA, prefixA, entryB, prefixB);
	}

	default List<DataEntry> gather(String key, String value, String... names) {
		return gather(key, value, Arrays.asList(names));
	}

	default List<DataEntry> gather(String key, String value, Collection<String> names) {
		return names.stream()
				.map(name -> gather(key, value, name))
				.map(entry -> entry.remove(new SelectionImpl(names)))
				.collect(Collectors.toList());
	}

	default DataEntry gather(String key, String value, String name) {
		return this.mutate(key, entry -> new StringDataValue(name))
				.mutate(value, DataEntry::get, name)
				.remove(name);
	}

	default DataEntry mutate(Mutation mutation) {
		return new MutatedDataEntry(this, mutation);
	}

	default DataEntry mutate(String name, Function<DataEntry, DataValue> mutation) {
		return new MutatedDataEntry(this, name, mutation);
	}

	default <T, U> DataEntry mutate(String name, BiFunction<DataEntry, U, DataValue> mutation, U u) {
		return new MutatedDataEntry(this, name, mutation, u);
	}

	default DataEntry remove(Selection selection) {
		return new RemovedDataEntry(this, selection);
	}

	default DataEntry remove(Collection<String> names) {
		return new RemovedDataEntry(this, names);
	}

	default DataEntry remove(String... names) {
		return new RemovedDataEntry(this, names);
	}

	default DataEntry rename(Rename rename) {
		return new RenamedDataEntry(this, rename);
	}

	default DataEntry rename(String newName, String oldName) {
		return new RenamedDataEntry(this, newName, oldName);
	}

	default DataEntry select(Selection selection) {
		return new SelectedDataEntry(this, selection);
	}

	default DataEntry select(Collection<String> names) {
		return new SelectedDataEntry(this, names);
	}

	default DataEntry select(String... names) {
		return new SelectedDataEntry(this, names);
	}

	default DataEntry separate(String key, String separator, List<String> names) {
		DataEntry entry = this;
		for (int i = 0; i < names.size(); i++) {
			entry = entry.mutate(Mutation.split(names.get(i), key, separator, i));
		}
		return entry;
	}

	// TODO tidyr::spread
	// TODO tidyr::separate_rows
	// TODO tidyr::unite
	// TODO tidyr::extract

	// TODO dplyr::transmute
	// TODO dplyr::mutate_all
	// TODO dplyr::mutate_if
	// TODO dplyr::mutate_at

}
