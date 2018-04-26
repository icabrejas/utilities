package org.utilities.core.dataframe.entry.select;

import java.util.Collection;

import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.lang.iterable.IterablePipe;

public class SelectedDataEntry implements DataEntry {

	private DataEntry entry;
	private Selection selection;

	public SelectedDataEntry(DataEntry entry, Selection selection) {
		this.entry = entry;
		this.selection = selection;
	}

	public static SelectedDataEntry from(DataEntry entry, Selection selection) {
		return new SelectedDataEntry(entry, selection);
	}

	public static SelectedDataEntry from(DataEntry entry, Collection<String> keys) {
		return from(entry, Selection.from(keys));
	}

	@Override
	public Collection<String> keys() {
		return IterablePipe.from(entry.keys())
				.filter(selection::test)
				.toList();
	}

	@Override
	public DataValue get(String key) {
		return selection.test(key) ? entry.get(key) : null;
	}

}
