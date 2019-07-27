package org.utilities.core.time;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.List;

public abstract class InstantTransform {

	public static final InstantTransform YEAR = new InstantTransform(ChronoField.YEAR) {

		@Override
		String extract(String date) {
			return 4 <= date.length() ? date.substring(0, 4) : "";
		}

	};
	public static final InstantTransform MONTH = new InstantTransform(ChronoField.MONTH_OF_YEAR) {

		@Override
		String extract(String date) {
			return 7 <= date.length() ? date.substring(5, 7) : "";
		}

	};
	public static final InstantTransform DAY = new InstantTransform(ChronoField.DAY_OF_MONTH) {

		@Override
		String extract(String date) {
			return 10 <= date.length() ? date.substring(8, 10) : "";
		}

	};
	public static final InstantTransform HOUR = new InstantTransform(ChronoField.HOUR_OF_DAY) {

		@Override
		String extract(String date) {
			return 13 <= date.length() ? date.substring(11, 13) : "";
		}

	};
	public static final InstantTransform MINUTE = new InstantTransform(ChronoField.MINUTE_OF_HOUR) {

		@Override
		String extract(String date) {
			return 16 <= date.length() ? date.substring(14, 16) : "";
		}

	};
	public static final InstantTransform SECOND = new InstantTransform(ChronoField.SECOND_OF_MINUTE) {

		@Override
		String extract(String date) {
			return 19 <= date.length() ? date.substring(17, 19) : "";
		}

	};
	public static final InstantTransform MILLISECOND = new InstantTransform(ChronoField.MILLI_OF_SECOND) {

		@Override
		String extract(String date) {
			return 23 <= date.length() ? date.substring(20, 23) : "";
		}

	};

	private static final List<InstantTransform> TRANSFORMS = Arrays.asList(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND,
			MILLISECOND);

	private TemporalField field;

	private InstantTransform(TemporalField field) {
		this.field = field;
	}

	abstract String extract(String date);

	public Instant apply(Instant instant, String date) {
		date = extract(date);
		if (!date.isEmpty()) {
			instant = instant.with(field, Integer.parseInt(date));
		} else {
			instant = Instant.ofEpochMilli(instant.toEpochMilli());
		}
		return instant;
	}

	public static Instant applyAll(Instant instant, String date) {
		for (InstantTransform transform : InstantTransform.TRANSFORMS) {
			instant = transform.apply(instant, date);
		}
		return Instant.ofEpochMilli(instant.toEpochMilli());
	}

}
