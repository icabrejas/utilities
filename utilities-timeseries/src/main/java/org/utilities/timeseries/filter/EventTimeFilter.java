package org.utilities.timeseries.filter;

import java.time.Instant;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.filter.Filter;
import org.utilities.core.lang.iterable.filter.FilterImpl;
import org.utilities.core.util.function.PredicatePlus;
import org.utilities.timeseries.Event;

public class EventTimeFilter extends FilterImpl<Event> {

	private EventTimeFilter(Predicate<Instant> predicate) {
		super(metadata -> predicate.test(metadata.getTime()));
	}

	public static Filter<Event> isEquals(Instant unixtime) {
		return new EventTimeFilter(unixtime::equals);
	}

	public static Filter<Event> isEquals(int field, int value) {
		return new EventTimeFilter(unixtime -> value == unixtime.get(field));
	}

	public static Filter<Event> isHigher(Instant unixtime) {
		return new EventTimeFilter(unixtime::isHigherThan);
	}

	public static Filter<Event> isHigherOrEquals(Instant unixtime) {
		Filter<Event> filter = isLower(unixtime);
		return filter.negate();
	}

	public static Filter<Event> isLower(Instant unixtime) {
		return new EventTimeFilter(unixtime::isLowerThan);
	}

	public static Filter<Event> isLowerOrEquals(Instant unixtime) {
		Filter<Event> filter = isHigher(unixtime);
		return filter.negate();
	}

	public static Filter<Event> isBetween(Instant from, Instant to) {
		return new FilterImpl<>(PredicatePlus.and(isHigher(from), isLower(to)));
	}

	public static Filter<Event> isBetweenOrEquals(Instant from, Instant to) {
		return new FilterImpl<>(PredicatePlus.and(isHigherOrEquals(from), isLowerOrEquals(to)));
	}

}
