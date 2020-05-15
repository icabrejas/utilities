package org.utilities.symbolicmath.store.symbol;

import java.util.Collections;
import java.util.List;

import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.store.SymbolStoreConsumer;
import org.utilities.symbolicmath.symbol.Symbol;

public class Lag<T> implements Symbol<SymbolStore<T>, T>, SymbolStoreConsumer {

	private String label;
	private int lag;

	public Lag(String label, int lag) {
		this.label = label;
		this.lag = lag;
	}

	@Override
	public T apply(SymbolStore<T> store) {
		return store.lag(label, lag);
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public int getWindow() {
		return lag;
	}

	@Override
	public List<Symbol<SymbolStore<T>, ?>> dependencies() {
		return Collections.emptyList();
	}

}
