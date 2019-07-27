package org.utilities.symbolicmath.symbol;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

public interface InstantSymbol<S> extends Symbol<S, Instant> {

	static <S> InstantSymbol<S> symbol(Function<S, Instant> func) {
		if (func instanceof InstantSymbol) {
			return (InstantSymbol<S>) func;
		} else {
			return func::apply;
		}
	}

	static <S> InstantSymbol<S> constant(Instant constant) {
		return store -> constant;
	}

	default Symbol<S, Integer> field(TemporalField field) {
		return andThen(Instant::get, field);
	}

	default Symbol<S, Integer> year() {
		return field(ChronoField.YEAR);
	}

	default Symbol<S, Integer> month() {
		return field(ChronoField.MONTH_OF_YEAR);
	}

	default Symbol<S, Integer> dayOfYear() {
		return field(ChronoField.DAY_OF_YEAR);
	}

	default Symbol<S, Integer> dayOfMonth() {
		return field(ChronoField.DAY_OF_MONTH);
	}

	default Symbol<S, Integer> dayOfWeek() {
		return field(ChronoField.DAY_OF_WEEK);
	}

	default Symbol<S, Integer> hourOfDay() {
		return field(ChronoField.HOUR_OF_DAY);
	}

	default Symbol<S, Integer> minuteOfDay() {
		return field(ChronoField.MINUTE_OF_DAY);
	}

	default Symbol<S, Integer> minuteOfHour() {
		return field(ChronoField.MINUTE_OF_HOUR);
	}

	default Symbol<S, Integer> secondOfDay() {
		return field(ChronoField.SECOND_OF_DAY);
	}

	default Symbol<S, Integer> secondOfMinute() {
		return field(ChronoField.SECOND_OF_MINUTE);
	}

	default Symbol<S, Integer> milliOfDay() {
		return field(ChronoField.MILLI_OF_DAY);
	}

	default Symbol<S, Integer> milliOfSecond() {
		return field(ChronoField.MILLI_OF_SECOND);
	}

	default InstantSymbol<S> truncatedTo(TemporalUnit unit) {
		return andThen(Instant::truncatedTo, unit).asInstant();
	}

	default InstantSymbol<S> minus(TemporalAmount amountToSubtract) {
		return andThen(Instant::minus, amountToSubtract).asInstant();
	}

	default InstantSymbol<S> minus(long amountToSubtract, TemporalUnit unit) {
		return andThen(instant -> instant.minus(amountToSubtract, unit)).asInstant();
	}

	default InstantSymbol<S> minusMillis(long millisToSubtract) {
		return andThen(Instant::minusMillis, millisToSubtract).asInstant();
	}

	default InstantSymbol<S> minusNanos(long nanosToSubtract) {
		return andThen(Instant::minusNanos, nanosToSubtract).asInstant();
	}

	default InstantSymbol<S> minusSeconds(long secondsToSubtract) {
		return andThen(Instant::minusSeconds, secondsToSubtract).asInstant();
	}

	default InstantSymbol<S> plus(TemporalAmount amountToSubtract) {
		return andThen(Instant::plus, amountToSubtract).asInstant();
	}

	default InstantSymbol<S> plus(long amountToSubtract, TemporalUnit unit) {
		return andThen(instant -> instant.plus(amountToSubtract, unit)).asInstant();
	}

	default InstantSymbol<S> plusMillis(long millisToSubtract) {
		return andThen(Instant::plusMillis, millisToSubtract).asInstant();
	}

	default InstantSymbol<S> plusNanos(long nanosToSubtract) {
		return andThen(Instant::plusNanos, nanosToSubtract).asInstant();
	}

	default InstantSymbol<S> plusSeconds(long secondsToSubtract) {
		return andThen(Instant::plusSeconds, secondsToSubtract).asInstant();
	}

	default BooleanSymbol<S> isAfter(InstantSymbol<S> otherInstant) {
		return andThen(Instant::isAfter, otherInstant).asBoolean();
	}

	default BooleanSymbol<S> isBefore(InstantSymbol<S> otherInstant) {
		return andThen(Instant::isBefore, otherInstant).asBoolean();
	}

}
