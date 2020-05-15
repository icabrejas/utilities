package org.utilities.symbolicmath.symbol;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

public interface SymbolInstant<S> extends Symbol<S, Instant> {

	static <S> SymbolInstant<S> empty() {
		return constant(null);
	}

	static <S> SymbolInstant<S> constant(Instant constant) {
		return s -> constant;
	}

	static <S> SymbolInstant<S> as(Function<S, Instant> func) {
		if (func instanceof SymbolInstant) {
			return (SymbolInstant<S>) func;
		} else {
			return func::apply;
		}
	}

	default SymbolDouble<S> field(Symbol<S, TemporalField> field) {
		return SymbolDouble.as(andThenNotNullEval(Instant::get, field).andThenNotNullEval(Integer::doubleValue));
	}

	default SymbolDouble<S> year() {
		return field(Symbol.constant(ChronoField.YEAR));
	}

	default SymbolDouble<S> month() {
		return field(Symbol.constant(ChronoField.MONTH_OF_YEAR));
	}

	default SymbolDouble<S> dayOfYear() {
		return field(Symbol.constant(ChronoField.DAY_OF_YEAR));
	}

	default SymbolDouble<S> dayOfMonth() {
		return field(Symbol.constant(ChronoField.DAY_OF_MONTH));
	}

	default SymbolDouble<S> dayOfWeek() {
		return field(Symbol.constant(ChronoField.DAY_OF_WEEK));
	}

	default SymbolDouble<S> hourOfDay() {
		return field(Symbol.constant(ChronoField.HOUR_OF_DAY));
	}

	default SymbolDouble<S> minuteOfDay() {
		return field(Symbol.constant(ChronoField.MINUTE_OF_DAY));
	}

	default SymbolDouble<S> minuteOfHour() {
		return field(Symbol.constant(ChronoField.MINUTE_OF_HOUR));
	}

	default SymbolDouble<S> secondOfDay() {
		return field(Symbol.constant(ChronoField.SECOND_OF_DAY));
	}

	default SymbolDouble<S> secondOfMinute() {
		return field(Symbol.constant(ChronoField.SECOND_OF_MINUTE));
	}

	default SymbolDouble<S> milliOfDay() {
		return field(Symbol.constant(ChronoField.MILLI_OF_DAY));
	}

	default SymbolDouble<S> milliOfSecond() {
		return field(Symbol.constant(ChronoField.MILLI_OF_SECOND));
	}

	default SymbolInstant<S> truncatedTo(Symbol<S, TemporalUnit> unit) {
		return as(andThenNotNullEval(Instant::truncatedTo, unit));
	}

	default SymbolInstant<S> minus(Symbol<S, TemporalAmount> amountToSubtract) {
		return as(andThenNotNullEval(Instant::minus, amountToSubtract));
	}

	// FIXME check null cases
	default SymbolInstant<S> minus(Symbol<S, Long> amountToSubtract, Symbol<S, TemporalUnit> unit) {
		return s -> apply(s).minus(amountToSubtract.apply(s), unit.apply(s));
	}

	default SymbolInstant<S> minusMillis(Symbol<S, Long> millisToSubtract) {
		return as(andThenNotNullEval(Instant::minusMillis, millisToSubtract));
	}

	default SymbolInstant<S> minusNanos(Symbol<S, Long> nanosToSubtract) {
		return as(andThenNotNullEval(Instant::minusNanos, nanosToSubtract));
	}

	default SymbolInstant<S> minusSeconds(Symbol<S, Long> secondsToSubtract) {
		return as(andThenNotNullEval(Instant::minusSeconds, secondsToSubtract));
	}

	default SymbolInstant<S> plus(Symbol<S, TemporalAmount> amountToSubtract) {
		return as(andThenNotNullEval(Instant::plus, amountToSubtract));
	}

	// FIXME check null cases
	default SymbolInstant<S> plus(Symbol<S, Long> amountToSubtract, Symbol<S, TemporalUnit> unit) {
		return s -> apply(s).plus(amountToSubtract.apply(s), unit.apply(s));
	}

	default SymbolInstant<S> plusMillis(Symbol<S, Long> millisToSubtract) {
		return as(andThenNotNullEval(Instant::plusMillis, millisToSubtract));
	}

	default SymbolInstant<S> plusNanos(Symbol<S, Long> nanosToSubtract) {
		return as(andThenNotNullEval(Instant::plusNanos, nanosToSubtract));
	}

	default SymbolInstant<S> plusSeconds(Symbol<S, Long> secondsToSubtract) {
		return as(andThenNotNullEval(Instant::plusSeconds, secondsToSubtract));
	}

	default SymbolBoolean<S> isAfter(Symbol<S, Instant> otherInstant) {
		return SymbolBoolean.as(andThenNotNullEval(Instant::isAfter, otherInstant));
	}

	default SymbolBoolean<S> isBefore(Symbol<S, Instant> otherInstant) {
		return SymbolBoolean.as(andThenNotNullEval(Instant::isBefore, otherInstant));
	}

}
