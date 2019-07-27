package org.utilities.dataframe;

import java.util.Iterator;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.dataframe.dataentry.DataEntry;

public class DataFrameImpl<E extends DataEntry> implements DataFrame {

	private IterablePipe<DataEntry> it;

	public DataFrameImpl(IterablePipe<DataEntry> it) {
		this.it = it;
	}

	@Override
	public Iterator<DataEntry> iterator() {
		return it.iterator();
	}

}
