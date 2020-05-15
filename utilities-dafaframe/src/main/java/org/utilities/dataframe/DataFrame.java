package org.utilities.dataframe;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.batch.IPBatchSemaphore;
import org.utilities.core.lang.iterable.braid.IPBraided;
import org.utilities.core.lang.iterable.limit.IPLimitedStopCriteria;
import org.utilities.core.lang.iterable.track.IPTracked;
import org.utilities.dataframe.bind.DFBindRows;
import org.utilities.dataframe.bind.DFRowBinded;
import org.utilities.dataframe.cell.DFCell;
import org.utilities.dataframe.column.DFColumn;
import org.utilities.dataframe.row.DFRow;
import org.utilities.dataframe.row.DFRowImpl;
import org.utilities.dataframe.symbol.DFStore;
import org.utilities.dataframe.symbol.DFSymbolUtils;
import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.SymbolBoolean;
import org.utilities.symbolicmath.symbol.SymbolDouble;
import org.utilities.symbolicmath.symbol.SymbolInstant;

public interface DataFrame extends IterablePipe<DFRow> {

	default SymbolStore<DFCell> store() {
		return new DFStore();
	}

	public static DataFrame as(Iterable<DFRow> rows) {
		DFStore store = new DFStore();
		return new DataFrameImpl(new IPTracked<>(rows, store), store);
	}

	public static DataFrame as(DFColumn column, String name) {
		return DataFrame.as(column.map(cell -> new DFRowImpl(name, cell)));
	}

	// TODO it's necessary
	default Stream<DFRow> stream(int characteristics, boolean parallel) {
		Spliterator<DFRow> spliterator = Spliterators.spliteratorUnknownSize(iterator(), characteristics);
		return StreamSupport.stream(spliterator, parallel);
	}

	default DataFrame put(String name, DFColumn column) {
		// FIXME null prefixes
		return remove(name).bindColumns(null, DataFrame.as(column, name), null);
	}

	default DataFrame bindRows(DataFrame other) {
		return new DFBindRows(this, other);
	}

	default DataFrame bindColumns(String thisPrefix, DataFrame other, String otherPrefix) {
		return bindColumns(Arrays.asList(thisPrefix, otherPrefix), Arrays.asList(this, other));
	}

	public static DataFrame bindColumns(List<String> prefixes, List<DataFrame> dataFrames) {
		return IPBraided.create(dataFrames)
				.batch(IPBatchSemaphore.tuples(dataFrames.size()))
				.map(tuple -> (DFRow) new DFRowBinded(prefixes, tuple))
				.apply(DataFrame::as);
	}

	default DataFrame gather(String key, String value, String... names) {
		return as(flatMap(entry -> entry.gather(key, value, names)));
	}

	default DataFrame gather(String key, String value, Collection<String> names) {
		return as(flatMap(entry -> entry.gather(key, value, names)));
	}

	// ---------------------- spread ----------------------------------------

	default DataFrame mutate(String name, Symbol<SymbolStore<DFCell>, DFCell> symbol, SymbolStore<DFCell> store) {
		return as(map(DFSymbolUtils.asMapper(name, symbol, store, true)));
	}

	default DataFrame mutate(String name, Symbol<SymbolStore<DFCell>, DFCell> symbol) {
		return mutate(name, symbol, store());
	}

	default DataFrame mutate(String name, SymbolDouble<SymbolStore<DFCell>> symbol, SymbolStore<DFCell> store) {
		return as(map(DFSymbolUtils.asMapper(name, symbol, store, true)));
	}

	default DataFrame mutate(String name, SymbolDouble<SymbolStore<DFCell>> symbol) {
		return mutate(name, symbol, store());
	}

	default DataFrame mutate(String name, SymbolBoolean<SymbolStore<DFCell>> symbol, SymbolStore<DFCell> store) {
		return as(map(DFSymbolUtils.asMapper(name, symbol, store, true)));
	}

	default DataFrame mutate(String name, SymbolBoolean<SymbolStore<DFCell>> symbol) {
		return mutate(name, symbol, store());
	}

	default DataFrame mutate(String name, SymbolInstant<SymbolStore<DFCell>> symbol, SymbolStore<DFCell> store) {
		return as(map(DFSymbolUtils.asMapper(name, symbol, store, true)));
	}

	default DataFrame mutate(String name, SymbolInstant<SymbolStore<DFCell>> symbol) {
		return mutate(name, symbol, store());
	}

	default DataFrame select(Predicate<String> selection) {
		return as(map(DFRow::select, selection));
	}

	default DataFrame select(Collection<String> names) {
		return as(map(DFRow::select, names));
	}

	default DataFrame select(String... names) {
		return as(map(DFRow::select, names));
	}

	default DataFrame remove(Predicate<String> selection) {
		return as(map(DFRow::remove, selection));
	}

	default DataFrame remove(Collection<String> names) {
		return as(map(DFRow::remove, names));
	}

	default DataFrame remove(String... names) {
		return as(map(DFRow::remove, names));
	}

	default DataFrame rename(Function<String, String> translator) {
		return as(map(DFRow::rename, translator));
	}

	default DataFrame rename(Map<String, String> translations) {
		return as(map(DFRow::rename, translations));
	}

	default DataFrame rename(String oldName, String newName) {
		return as(map(entry -> entry.rename(oldName, newName)));
	}

	default DataFrame head() {
		return head(6);
	}

	default DataFrame head(int lines) {
		return as(limit(IPLimitedStopCriteria.limit(lines)));
	}

	default DFColumn get(String name) {
		return map(DFRow::get, name).apply(DFColumn::asColumn);
	}

}
