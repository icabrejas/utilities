package org.utilities.symbolicmath.store.symbol;

import java.util.Collections;
import java.util.List;

import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.store.SymbolStoreConsumer;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.array.SymbolArray;

public class Range<T> implements SymbolArray<SymbolStore<T>, T>, SymbolStoreConsumer {

	private String label;
	private int window;

	public Range(String label, int window) {
		this.label = label;
		this.window = window;
	}

	@Override
	public T[] apply(SymbolStore<T> store) {
		return store.range(label, window);
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public int getWindow() {
		return window;
	}

	@Override
	public List<Symbol<SymbolStore<T>, ?>> dependencies() {
		return Collections.emptyList();
	}

}
