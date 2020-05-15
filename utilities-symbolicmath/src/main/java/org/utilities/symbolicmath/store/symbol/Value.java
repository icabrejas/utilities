package org.utilities.symbolicmath.store.symbol;

import java.util.Collections;
import java.util.List;

import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.store.SymbolStoreConsumer;
import org.utilities.symbolicmath.symbol.Symbol;

public class Value<T> implements Symbol<SymbolStore<T>, T>, SymbolStoreConsumer {

	private String label;

	public Value(String label) {
		this.label = label;
	}

	@Override
	public T apply(SymbolStore<T> store) {
		return store.get(label);
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public int getWindow() {
		return 1;
	}

	@Override
	public List<Symbol<SymbolStore<T>, ?>> dependencies() {
		return Collections.emptyList();
	}

}
