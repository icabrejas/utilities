package org.utilities.dataframe.symbol;

import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.filter.DataEntryFilter;
import org.utilities.symbolicmath.symbol.BooleanSymbol;

public class SymbolFilter {

	public static DataEntryFilter<DataEntry> filter(BooleanSymbol<DataEntry> symbol) {
		return new DataEntryFilter<>(symbol::apply);
	}

}
