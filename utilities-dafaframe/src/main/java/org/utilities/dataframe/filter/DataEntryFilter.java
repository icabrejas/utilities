package org.utilities.dataframe.filter;

import java.util.function.Predicate;

import org.utilities.core.lang.iterable.filter.Filter;
import org.utilities.core.lang.iterable.filter.FilterImpl;
import org.utilities.core.util.function.PredicatePlus;
import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.DataValue;

public class DataEntryFilter<E extends DataEntry> extends FilterImpl<E> {

	public DataEntryFilter(Predicate<E> emit) {
		super(emit);
	}

	public static <E extends DataEntry> Filter<E> isEquals(String name, DataValue value) {
		return new DataValueFilter<>(name, value::equals);
	}

	public static <E extends DataEntry> Filter<E> isNotEquals(String name, DataValue value) {
		Filter<E> filter = isEquals(name, value);
		return filter.negate();
	}

	public static <E extends DataEntry> Filter<E> isHigher(String name, DataValue value) {
		return new DataValueFilter<>(name, value::isLowerThan);
	}

	public static <E extends DataEntry> Filter<E> isHigherOrEquals(String name, DataValue value) {
		Filter<E> filter = isLower(name, value);
		return filter.negate();
	}

	public static <E extends DataEntry> Filter<E> isLower(String name, DataValue value) {
		return new DataValueFilter<>(name, value::isHigherThan);
	}

	public static <E extends DataEntry> Filter<E> isLowerOrEquals(String name, DataValue value) {
		Filter<E> filter = isHigher(name, value);
		return filter.negate();
	}

	public static <E extends DataEntry> Filter<E> isBetween(String name, DataValue min, DataValue max) {
		return new FilterImpl<>(PredicatePlus.and(isHigher(name, min), isLower(name, max)));
	}

	public static <E extends DataEntry> Filter<E> isBetweenOrEquals(String name, DataValue min, DataValue max) {
		return new FilterImpl<>(PredicatePlus.and(isHigherOrEquals(name, min), isLowerOrEquals(name, max)));
	}

	public static <E extends DataEntry> Filter<E> isNull(String name) {
		return new DataValueFilter<>(name, PredicatePlus.isNull());
	}

	public static <E extends DataEntry> Filter<E> isNotNull(String name) {
		Filter<E> filter = isNull(name);
		return filter.negate();
	}

}
