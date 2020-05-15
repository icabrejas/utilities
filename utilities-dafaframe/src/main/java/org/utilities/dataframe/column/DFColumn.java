package org.utilities.dataframe.column;

import java.util.function.Function;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.dataframe.cell.DFCell;

// TODO usefull???
public interface DFColumn extends IterablePipe<DFCell> {

	public static DFColumn asColumn(Iterable<DFCell> values) {
		return values::iterator;
	}

	default DFColumn mutate(Function<DFCell, DFCell> mutation) {
		return asColumn(map(mutation));
	}

}
