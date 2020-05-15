package org.utilities.dataframe.symbol;

import java.util.Map;

import org.utilities.core.lang.iterable.track.IPTrackerImpl;
import org.utilities.core.util.function.AppendOpt;
import org.utilities.dataframe.cell.DFCell;
import org.utilities.dataframe.row.DFRow;
import org.utilities.symbolicmath.store.SSLocal;
import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.store.SymbolStoreConsumer;

public class DFStore extends IPTrackerImpl<DFRow> implements SymbolStore<DFCell> {

	private long index;
	private SSLocal<DFCell> store;

	public DFStore() {
		this.store = new SSLocal<>(DFCell[]::new);
		start(AppendOpt.After, this::clear);
		next(AppendOpt.After, this::update);
	}

	private void clear() {
		index = -1;
		store.clear();
	}

	private void update(DFRow row) {
		store.moveTo(++index);
		for (Map.Entry<String, DFCell> cell : row) {
			store.put(index, cell.getKey(), cell.getValue());
		}
	}

	@Override
	public void put(String label, DFCell cell) {
		store.put(label, cell);
	}

	@Override
	public DFCell get(String label) {
		return store.get(label);
	}

	@Override
	public DFCell lag(String label, int lag) {
		return store.lag(label, lag);
	}

	@Override
	public DFCell[] range(String label, int window) {
		return store.range(label, window);
	}

	@Override
	public void subscribe(SymbolStoreConsumer subscriber) {
		store.subscribe(subscriber);
	}

}
