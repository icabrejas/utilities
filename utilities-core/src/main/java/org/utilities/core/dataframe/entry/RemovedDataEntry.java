package org.utilities.core.dataframe.entry;

import java.util.Collection;

import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.util.function.PredicatePlus;

public class RemovedDataEntry implements DataEntry {

	private DataEntry entry;
	private Collection<String> names;

	public RemovedDataEntry(DataEntry entry, Collection<String> names) {
		this.entry = entry;
		this.names = names;
	}

	@Override
	public Collection<String> names() {
		return IterablePipe.newInstance(entry.names())
				.filter(PredicatePlus.negate(names::contains))
				.toList();
	}

	@Override
	public DataValue get(String name) {
		return !names.contains(name) ? entry.get(name) : null;
	}
}
