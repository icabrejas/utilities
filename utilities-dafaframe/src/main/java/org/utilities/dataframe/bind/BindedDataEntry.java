package org.utilities.dataframe.bind;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.DataValue;

public class BindedDataEntry implements DataEntry {

	private static final char SEPARATOR = '.';
	private List<DataEntry> dataEntries;
	private List<String> prefixes;

	public BindedDataEntry(List<DataEntry> dataEntries, List<String> prefixes) {
		if (prefixes.size() != dataEntries.size()) {
			throw new Error(); // TODO spefific exception
		}
		this.dataEntries = dataEntries;
		this.prefixes = prefixes;
	}

	public BindedDataEntry(DataEntry entryA, String prefixA, DataEntry entryB, String prefixB) {
		this(Arrays.asList(entryA, entryB), Arrays.asList(prefixA, prefixB));
	}

	@Override
	public Collection<String> keys() {
		Set<String> names = new TreeSet<>();
		for (int i = 0; i < prefixes.size(); i++) {
			String prefix = prefixes.get(i);
			DataEntry entry = dataEntries.get(i);
			names.addAll(names(prefix, entry));
		}
		return names;
	}

	private Set<String> names(String prefix, DataEntry entry) {
		return IterablePipe.newInstance(entry.keys())
				.map(BindedDataEntry::name, prefix)
				.toSet();
	}

	private static String name(String name, String prefix) {
		return prefix + SEPARATOR + name;
	}

	@Override
	public DataValue get(String name) {
		for (int i = 0; i < prefixes.size(); i++) {
			String prefix = prefixes.get(i);
			if (name.startsWith(prefix + '.')) {
				DataEntry entry = dataEntries.get(i);
				return entry.get(name.substring(prefix.length() + 1));
			}
		}
		return null;
	}

}
