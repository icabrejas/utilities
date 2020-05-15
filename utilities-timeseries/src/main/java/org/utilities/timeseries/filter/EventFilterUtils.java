package org.utilities.timeseries.filter;

import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.function.Predicate;

import org.utilities.core.lang.iterable.filter.IPFilter;
import org.utilities.core.lang.iterable.filter.IPFilterImpl;
import org.utilities.core.util.function.PredicatePlus;
import org.utilities.timeseries.Event;

public class EventFilterUtils {

	private static Predicate<Event> parse(Predicate<Instant> predicate) {
		return evt -> predicate.test(evt.getTime());
	}

	public static IPFilter<Event> isEquals(Instant time) {
		return new IPFilterImpl<>(parse(time::equals));
	}

	public static IPFilter<Event> isNotEquals(Instant time) {
		return new IPFilterImpl<>(parse(time::equals).negate());
	}

	public static IPFilter<Event> isEquals(TemporalField field, int value) {
		return new IPFilterImpl<>(parse(time -> value == time.get(field)));
	}

	public static IPFilter<Event> isNotEquals(TemporalField field, int value) {
		return new IPFilterImpl<>(parse(time -> value == time.get(field)).negate());
	}

	public static IPFilter<Event> isBefore(Instant time) {
		return new IPFilterImpl<>(parse(time::isAfter));
	}

	public static IPFilter<Event> isBeforeOrEquals(Instant time) {
		return new IPFilterImpl<>(parse(time::isBefore).negate());
	}

	public static IPFilter<Event> isAfter(Instant time) {
		return new IPFilterImpl<>(parse(time::isBefore));
	}

	public static IPFilter<Event> isAfterOrEquals(Instant time) {
		return new IPFilterImpl<>(parse(time::isAfter).negate());
	}

	public static IPFilter<Event> isBetween(Instant from, Instant to) {
		Predicate<Event> leftCondition = parse(from::isBefore);
		Predicate<Event> rightCondition = parse(to::isAfter);
		return new IPFilterImpl<>(PredicatePlus.and(leftCondition, rightCondition));
	}

	public static IPFilter<Event> isBetweenOrEquals(Instant from, Instant to) {
		Predicate<Event> leftCondition = parse(from::isAfter).negate();
		Predicate<Event> rightCondition = parse(to::isBefore).negate();
		return new IPFilterImpl<>(PredicatePlus.and(leftCondition, rightCondition));
	}

}
