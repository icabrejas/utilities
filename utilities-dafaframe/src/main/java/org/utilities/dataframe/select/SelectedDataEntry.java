package org.utilities.dataframe.select;

import java.util.Collection;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.DataValue;

public class SelectedDataEntry implements DataEntry {

	private DataEntry entry;
	private Selection selection;

	public SelectedDataEntry(DataEntry entry, Selection selection) {
		this.entry = entry;
		this.selection = selection;
	}

	public SelectedDataEntry(DataEntry entry, Collection<String> names) {
		this(entry, new SelectionImpl(names));
	}

	public SelectedDataEntry(DataEntry entry, String... names) {
		this(entry, new SelectionImpl(names));
	}

	@Override
	public Collection<String> keys() {
		return IterablePipe.newInstance(entry.keys())
				.filter(selection::test)
				.toList();
	}

	@Override
	public DataValue get(String key) {
		return selection.test(key) ? entry.get(key) : null;
	}

}
