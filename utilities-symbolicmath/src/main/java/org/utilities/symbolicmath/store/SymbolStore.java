package org.utilities.symbolicmath.store;

import org.utilities.symbolicmath.symbol.Symbol;

public interface SymbolStore<T> {

	void put(String label, T t);

	T get(String label);

	T lag(String label, int lag);

	T[] range(String label, int window);

	void subscribe(SymbolStoreConsumer subscriber);
	


	public static <T> SymbolStore<T> dummy(){
		return new SymbolStore<T>() {

			@Override
			public void put(String label, T t) {
			}

			@Override
			public T get(String label) {
				return null;
			}

			@Override
			public T lag(String label, int lag) {
				return null;
			}

			@Override
			public T[] range(String label, int window) {
				return null;
			}

			@Override
			public void subscribe(SymbolStoreConsumer subscriber) {
			}
		};
	}

	default void subscribe(Symbol<SymbolStore<T>, ?> symbol) {
		if (symbol instanceof SymbolStoreConsumer) {
			subscribe((SymbolStoreConsumer) symbol);
		}
		for (Symbol<SymbolStore<T>, ?> dependency : symbol.dependencies()) {
			subscribe(dependency);
		}
	}

}
