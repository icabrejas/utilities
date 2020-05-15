package org.utilities.dataframe.symbol;

import java.util.function.Function;

import org.utilities.core.lang.iterable.map.IPMapper;
import org.utilities.dataframe.cell.DFCell;
import org.utilities.dataframe.cell.DFCellBoolean;
import org.utilities.dataframe.cell.DFCellDouble;
import org.utilities.dataframe.cell.DFCellInstant;
import org.utilities.dataframe.map.DFMapUtils;
import org.utilities.dataframe.row.DFRow;
import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.store.rolling.RollingUtils;
import org.utilities.symbolicmath.store.rolling.SummarizerDouble;
import org.utilities.symbolicmath.store.symbol.Lag;
import org.utilities.symbolicmath.store.symbol.Range;
import org.utilities.symbolicmath.store.symbol.Value;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.SymbolBoolean;
import org.utilities.symbolicmath.symbol.SymbolDouble;
import org.utilities.symbolicmath.symbol.SymbolInstant;
import org.utilities.symbolicmath.symbol.array.SymbolArray;

public class DFSymbolUtils {

	public static Function<DFRow, DFCell> mutation(SymbolBoolean<SymbolStore<DFCell>> symbol, SymbolStore<DFCell> store,
			String name) {
		return mutation(symbol.andThen(DFCellBoolean::new), store, name);
	}

	public static Function<DFRow, DFCell> mutation(SymbolInstant<SymbolStore<DFCell>> symbol, SymbolStore<DFCell> store,
			String name) {
		return mutation(symbol.andThen(DFCellInstant::new), store, name);
	}

	public static Function<DFRow, DFCell> mutation(SymbolDouble<SymbolStore<DFCell>> symbol, SymbolStore<DFCell> store,
			String name) {
		return mutation(symbol.andThen(DFCellDouble::new), store, name);
	}

	public static Function<DFRow, DFCell> mutation(Symbol<SymbolStore<DFCell>, DFCell> symbol,
			SymbolStore<DFCell> store, String name) {
		return row -> {
			DFCell cell = symbol.apply(store);
			store.put(name, cell);
			return cell;
		};
	}

	public static Function<DFRow, DFCell> mutation(SymbolBoolean<SymbolStore<DFCell>> symbol,
			SymbolStore<DFCell> store) {
		return mutation(symbol.andThen(DFCellBoolean::new), store);
	}

	public static Function<DFRow, DFCell> mutation(SymbolInstant<SymbolStore<DFCell>> symbol,
			SymbolStore<DFCell> store) {
		return mutation(symbol.andThen(DFCellInstant::new), store);
	}

	public static Function<DFRow, DFCell> mutation(SymbolDouble<SymbolStore<DFCell>> symbol,
			SymbolStore<DFCell> store) {
		return mutation(symbol.andThen(DFCellDouble::new), store);
	}

	public static Function<DFRow, DFCell> mutation(Symbol<SymbolStore<DFCell>, DFCell> symbol,
			SymbolStore<DFCell> store) {
		return row -> symbol.apply(store);
	}

	public static IPMapper<DFRow, DFRow> asMapper(String name, Symbol<SymbolStore<DFCell>, DFCell> symbol,
			SymbolStore<DFCell> store, boolean producer) {
		store.subscribe(symbol);
		return DFMapUtils.FromDFRow.toCell(name, producer ? mutation(symbol, store, name) : mutation(symbol, store));
	}

	public static IPMapper<DFRow, DFRow> asMapper(String name, SymbolDouble<SymbolStore<DFCell>> symbol,
			SymbolStore<DFCell> store, boolean producer) {
		store.subscribe(symbol);
		return DFMapUtils.FromDFRow.toCell(name, producer ? mutation(symbol, store, name) : mutation(symbol, store));
	}

	public static IPMapper<DFRow, DFRow> asMapper(String name, SymbolBoolean<SymbolStore<DFCell>> symbol,
			SymbolStore<DFCell> store, boolean producer) {
		store.subscribe(symbol);
		return DFMapUtils.FromDFRow.toCell(name, producer ? mutation(symbol, store, name) : mutation(symbol, store));
	}

	public static IPMapper<DFRow, DFRow> asMapper(String name, SymbolInstant<SymbolStore<DFCell>> symbol,
			SymbolStore<DFCell> store, boolean producer) {
		store.subscribe(symbol);
		return DFMapUtils.FromDFRow.toCell(name, producer ? mutation(symbol, store, name) : mutation(symbol, store));
	}

	public static Symbol<SymbolStore<DFCell>, DFCell> get(String label) {
		return new Value<>(label);
	}

	public static Symbol<SymbolStore<DFCell>, DFCell> lag(String label, int lag) {
		return new Lag<>(label, lag);
	}

	public static SymbolArray<SymbolStore<DFCell>, DFCell> range(String label, int window) {
		return new Range<>(label, window);
	}

	public static SymbolDouble<SymbolStore<DFCell>> summarize(String label, int window, boolean nullOmit,
			SummarizerDouble<DFCell> summarizer) {
		return RollingUtils.summarize(label, window, nullOmit, DFCell::doubleValue, summarizer);
	}

}
