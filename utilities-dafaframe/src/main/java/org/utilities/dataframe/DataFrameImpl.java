package org.utilities.dataframe;

import java.util.Iterator;

import org.utilities.dataframe.cell.DFCell;
import org.utilities.dataframe.row.DFRow;
import org.utilities.symbolicmath.store.SymbolStore;

public class DataFrameImpl implements DataFrame {

	private Iterable<DFRow> rows;
	private SymbolStore<DFCell> store;

	public DataFrameImpl(Iterable<DFRow> rows, SymbolStore<DFCell> store) {
		this.rows = rows;
		this.store = store;
	}

	@Override
	public Iterator<DFRow> iterator() {
		return rows.iterator();
	}

	@Override
	public SymbolStore<DFCell> store() {
		return store;
	}

}
