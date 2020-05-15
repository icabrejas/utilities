package org.utilities.symbolicmath.store.rolling;

import org.utilities.core.util.function.FunctionPlus;
import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.symbol.SymbolDouble;
import org.utilities.symbolicmath.symbol.array.SymbolArrayDoublePrimitive;

public interface SummarizerDouble<T>
		extends FunctionPlus<SymbolArrayDoublePrimitive<SymbolStore<T>>, SymbolDouble<SymbolStore<T>>> {

}
