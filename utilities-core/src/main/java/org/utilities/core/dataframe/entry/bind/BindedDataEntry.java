package org.utilities.core.dataframe.entry.bind;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.lang.iterable.IterablePipe;

public class BindedDataEntry implements DataEntry {

	private static final char SEPARATOR = '.';
	private Map<String, DataEntry> entries;

	public BindedDataEntry(Map<String, DataEntry> entries) {
		this.entries = entries;
	}

	@Override
	public Collection<String> keys() {
		Set<String> names = new TreeSet<>();
		for (Map.Entry<String, DataEntry> entry : entries.entrySet()) {
			names.addAll(names(entry.getKey(), entry.getValue()));
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
		for (String prefix : entries.keySet()) {
			if (name.startsWith(prefix + '.')) {
				DataEntry entry = entries.get(prefix);
				return entry.get(name.substring(prefix.length() + 2));
			}
		}
		return null;
	}

}
