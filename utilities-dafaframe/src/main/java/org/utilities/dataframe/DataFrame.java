package org.utilities.dataframe;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.filter.Filter;
import org.utilities.core.lang.iterable.limit.StopCriteriaImpl;
import org.utilities.core.lang.iterable.observer.Observer;
import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.dataframe.filter.DataValueFilter;
import org.utilities.dataframe.mutate.Mutation;
import org.utilities.dataframe.rename.RenameImpl;
import org.utilities.dataframe.select.Selection;

public interface DataFrame extends IterablePipe<DataEntry> {

	public static DataFrame newInstance(Iterable<DataEntry> entries) {
		return entries::iterator;
	}

	// TODO
	// default BasicDataFrame bindColumns(String
	// prefixA, Iterable<DataEntry> entryB, String prefixB) {
	// Comparator<Map.Entry<Integer, E>> selector = Funnel.sequential(2);
	// List<Iterable<DataEntry>> sources = Arrays.asList(this, entryB);
	// new IterablePipeWoven<>(selector, sources);
	// return null;
	// }

	// TODO add other version with Selection
	default DataFrame gather(String key, String value, String... names) {
		return newInstance(flatMap(entry -> entry.gather(key, value, names)));
	}

	default DataFrame gather(String key, String value, Collection<String> names) {
		return newInstance(flatMap(entry -> entry.gather(key, value, names)));
	}

	default DataFrame mutate(Mutation mutation) {
		return newInstance(map(DataEntry::mutate, mutation));
	}

	default DataFrame mutate(String name, Function<DataEntry, DataValue> mutation) {
		return newInstance(map(entry -> entry.mutate(name, mutation)));
	}

	default <U> DataFrame mutate(String name, BiFunction<DataEntry, U, DataValue> mutation, U u) {
		return newInstance(map(entry -> entry.mutate(name, mutation, u)));
	}

	default DataFrame remove(Selection selection) {
		return newInstance(map(DataEntry::remove, selection));
	}

	default DataFrame remove(Collection<String> names) {
		return newInstance(map(DataEntry::remove, names));
	}

	default DataFrame remove(String... names) {
		return newInstance(map(DataEntry::remove, names));
	}

	default DataFrame rename(RenameImpl rename) {
		return newInstance(map(DataEntry::rename, rename));
	}

	default DataFrame rename(String newName, String oldName) {
		return newInstance(map(entry -> entry.rename(newName, oldName)));
	}

	default DataFrame select(Selection selection) {
		return newInstance(map(DataEntry::select, selection));
	}

	default DataFrame select(Collection<String> names) {
		return newInstance(map(DataEntry::select, names));
	}

	default DataFrame select(String... names) {
		return newInstance(map(DataEntry::select, names));
	}

	default DataFrame separate(String key, String separator, List<String> names) {
		return newInstance(map(entry -> entry.separate(key, separator, names)));
	}

	@Override
	default DataFrame observe(Observer<DataEntry> observer) {
		return newInstance(IterablePipe.super.observe(observer));
	}

	@Override
	default DataFrame observe(Consumer<DataEntry> observer) {
		return newInstance(IterablePipe.super.observe(observer));
	}

	@Override
	default <U> DataFrame observe(BiConsumer<DataEntry, U> observer, U u) {
		return newInstance(IterablePipe.super.observe(observer, u));
	}

	default DataFrame filterIsNull(String name) {
		return filter(DataValueFilter.isNull(name));
	}

	default DataFrame filterIsNotNull(String name) {
		return filter(DataValueFilter.isNotNull(name));
	}

	default DataFrame filterIsEquals(String name, DataValue value) {
		return filter(DataValueFilter.isEquals(name, value));
	}

	default DataFrame filterIsNotEquals(String name, DataValue value) {
		return filter(DataValueFilter.isNotEquals(name, value));
	}

	default DataFrame filterIsHigher(String name, DataValue value) {
		return filter(DataValueFilter.isHigher(name, value));
	}

	default DataFrame filterIsHigherOrEquals(String name, DataValue value) {
		return filter(DataValueFilter.isHigherOrEquals(name, value));
	}

	default DataFrame filterIsLower(String name, DataValue value) {
		return filter(DataValueFilter.isLower(name, value));
	}

	default DataFrame filterIsLowerOrEquals(String name, DataValue value) {
		return filter(DataValueFilter.isLowerOrEquals(name, value));
	}

	default DataFrame filterIsBetween(String name, DataValue min, DataValue max) {
		return filter(DataValueFilter.isBetween(name, min, max));
	}

	default DataFrame filterIsBetweenOrEquals(String name, DataValue min, DataValue max) {
		return filter(DataValueFilter.isBetweenOrEquals(name, min, max));
	}

	@Override
	default DataFrame filter(Filter<DataEntry> filter) {
		return newInstance(IterablePipe.super.filter(filter));
	}

	@Override
	default DataFrame filter(Predicate<DataEntry> filter) {
		return newInstance(IterablePipe.super.filter(filter));
	}

	@Override
	default <U> DataFrame filter(BiPredicate<DataEntry, U> filter, U u) {
		return newInstance(IterablePipe.super.filter(filter, u));
	}

	@Override
	default DataFrame skip(int times) {
		return newInstance(IterablePipe.super.skip(times));
	}

	@Override
	default DataFrame limit(StopCriteriaImpl<DataEntry> stop) {
		return newInstance(IterablePipe.super.limit(stop));
	}

	@Override
	default DataFrame limit(Predicate<DataEntry> stop) {
		return newInstance(IterablePipe.super.limit(stop));
	}

	@Override
	default <U> DataFrame limit(BiPredicate<DataEntry, U> stop, U u) {
		return newInstance(IterablePipe.super.limit(stop, u));
	}

	@Override
	default DataFrame limit(int times) {
		return newInstance(IterablePipe.super.limit(times));
	}

}
