package org.utilities.dataframe.filter;

import java.util.function.Predicate;

import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.DataValue;

public class DataValueFilter<E extends DataEntry> extends DataEntryFilter<E> {

	public DataValueFilter(String name, Predicate<DataValue> predicate) {
		super(entry -> predicate.test(entry.get(name)));
	}

}
