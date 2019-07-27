package org.utilities.dataframe.remove;

import java.util.Collection;

import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.dataframe.select.SelectedDataEntry;
import org.utilities.dataframe.select.Selection;
import org.utilities.dataframe.select.SelectionImpl;

public class RemovedDataEntry implements DataEntry {

	private SelectedDataEntry entry;

	public RemovedDataEntry(DataEntry entry, Selection selection) {
		this.entry = new SelectedDataEntry(entry, selection.negate());
	}

	public RemovedDataEntry(DataEntry entry, Collection<String> names) {
		this(entry, new SelectionImpl(names));
	}

	public RemovedDataEntry(DataEntry entry, String... names) {
		this(entry, new SelectionImpl(names));
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
