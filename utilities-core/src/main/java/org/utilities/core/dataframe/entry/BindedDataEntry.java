package org.utilities.core.dataframe.entry;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.utilities.core.dataframe.entry.value.DataValue;

public class BindedDataEntry implements DataEntry {

	private Map<String, DataEntry> entries;

	public BindedDataEntry(Map<String, DataEntry> entries) {
		this.entries = entries;
	}

	@Override
	public Collection<String> names() {
		Set<String> names = new TreeSet<>();
		for (Map.Entry<String, DataEntry> entry : entries.entrySet()) {
			names.addAll(names(entry.getKey(), entry.getValue()));
		}
		return names;
	}

	private Set<String> names(String prefix, DataEntry entry) {
		return entry.names()
				.stream()
				.map(name -> name(prefix, name))
				.collect(Collectors.toSet());
	}

	private String name(String prefix, String name) {
		return prefix + '.' + name;
	}

	@Override
	public DataValue get(String name) {
		for (String prefix : entries.keySet()) {
			if (name.startsWith(prefix + '.')) {
				DataEntry entry = entries.get(prefix);
				return entry.get(name(prefix, name));
			}
		}
		return null;
	}

}
