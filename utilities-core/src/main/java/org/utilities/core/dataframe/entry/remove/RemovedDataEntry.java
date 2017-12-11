package org.utilities.core.dataframe.entry.remove;

import java.util.Collection;

import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.select.SelectedDataEntry;
import org.utilities.core.dataframe.entry.select.Selection;
import org.utilities.core.dataframe.entry.value.DataValue;

public class RemovedDataEntry implements DataEntry {

	private SelectedDataEntry entry;

	public RemovedDataEntry(DataEntry entry, Selection selection) {
		this.entry = new SelectedDataEntry(entry, selection.negate());
	}

	public static RemovedDataEntry newInstance(DataEntry entry, Selection selection) {
		return new RemovedDataEntry(entry, selection);
	}

	public static RemovedDataEntry newInstance(DataEntry entry, Collection<String> keys) {
		return newInstance(entry, Selection.newInstance(keys));
	}

	@Override
	public Collection<String> keys() {
		return entry.keys();
	}

	@Override
	public DataValue get(String key) {
		return entry.get(key);
	}

}
