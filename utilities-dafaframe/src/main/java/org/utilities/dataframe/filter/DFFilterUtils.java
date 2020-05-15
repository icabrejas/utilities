package org.utilities.dataframe.filter;

import java.util.function.Predicate;

import org.utilities.core.lang.iterable.filter.IPFilter;
import org.utilities.core.lang.iterable.filter.IPFilterImpl;
import org.utilities.core.util.function.PredicatePlus;
import org.utilities.dataframe.cell.DFCell;
import org.utilities.dataframe.row.DFRow;

public class DFFilterUtils {

	private static Predicate<DFRow> parse(String name, Predicate<DFCell> predicate) {
		return row -> predicate.test(row.get(name));
	}

	public static IPFilter<DFRow> isNull(String name) {
		return IPFilter.concurrent(parse(name, PredicatePlus.isNull()));
	}

	public static IPFilter<DFRow> isNotNull(String name) {
		return IPFilter.concurrent(parse(name, PredicatePlus.isNotNull()));
	}

	public static IPFilter<DFRow> isEquals(String name, DFCell value) {
		return new IPFilterImpl<>(parse(name, value::equals));
	}

	public static IPFilter<DFRow> isNotEquals(String name, DFCell value) {
		return new IPFilterImpl<>(parse(name, value::equals).negate());
	}

	public static IPFilter<DFRow> isHigher(String name, DFCell value) {
		return new IPFilterImpl<>(parse(name, value::isLowerThan));
	}

	public static IPFilter<DFRow> isHigherOrEquals(String name, DFCell value) {
		return new IPFilterImpl<>(parse(name, value::isHigherThan).negate());
	}

	public static IPFilter<DFRow> isLower(String name, DFCell value) {
		return new IPFilterImpl<>(parse(name, value::isHigherThan));
	}

	public static IPFilter<DFRow> isLowerOrEquals(String name, DFCell value) {
		return new IPFilterImpl<>(parse(name, value::isLowerThan).negate());
	}

	public static IPFilter<DFRow> isBetween(String name, DFCell min, DFCell max) {
		Predicate<DFRow> leftCondition = parse(name, min::isLowerThan);
		Predicate<DFRow> rightCondition = parse(name, max::isHigherThan);
		return new IPFilterImpl<>(PredicatePlus.and(leftCondition, rightCondition));
	}

	public static IPFilter<DFRow> isBetweenOrEquals(String name, DFCell min, DFCell max) {
		Predicate<DFRow> leftCondition = parse(name, min::isHigherThan).negate();
		Predicate<DFRow> rightCondition = parse(name, max::isLowerThan).negate();
		return new IPFilterImpl<>(PredicatePlus.and(leftCondition, rightCondition));
	}

}
