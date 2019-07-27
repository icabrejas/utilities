package org.utilities.dataframe.rename;

import java.util.Collection;
import java.util.stream.Collectors;

import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.DataValue;

public class RenamedDataEntry implements DataEntry {

	private Rename rename;
	private DataEntry entry;

	public RenamedDataEntry(DataEntry entry, Rename rename) {
		this.entry = entry;
		this.rename = rename;
	}

	public RenamedDataEntry(DataEntry entry, String newName, String oldName) {
		this(entry, new RenameImpl(newName, oldName));
	}

	@Override
	public Collection<String> keys() {
		return entry.keys()
				.stream()
				.map(rename::translate)
				.collect(Collectors.toList());
	}

	@Override
	public DataValue get(String key) {
		return entry.get(rename.translate(key));
	}

}
