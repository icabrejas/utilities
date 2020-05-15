package org.utilities.dataframe.bind;

import org.utilities.core.lang.iterable.IterablePipeBind;
import org.utilities.dataframe.DataFrame;
import org.utilities.dataframe.row.DFRow;

public class DFBindRows extends IterablePipeBind<DFRow> implements DataFrame {

	public DFBindRows(DataFrame first, DataFrame second) {
		super(first, second);
	}

}
