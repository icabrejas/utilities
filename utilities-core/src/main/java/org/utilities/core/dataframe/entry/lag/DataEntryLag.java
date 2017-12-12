package org.utilities.core.dataframe.entry.lag;

import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;

public interface DataEntryLag extends DataEntry {

	DataValue lag(String key);
	
}
