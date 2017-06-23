package org.utilities.core.time;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public abstract class UnixtimeTransform {

	public static final UnixtimeTransform YEAR = new UnixtimeTransform(Calendar.YEAR) {

		@Override
		String extract(String date) {
			return 4 <= date.length() ? date.substring(0, 4) : "";
		}

	};
	public static final UnixtimeTransform MONTH = new UnixtimeTransform(Calendar.MONTH) {

		@Override
		String extract(String date) {
			return 7 <= date.length() ? date.substring(5, 7) : "";
		}

	};
	public static final UnixtimeTransform DAY = new UnixtimeTransform(Calendar.DAY_OF_MONTH) {

		@Override
		String extract(String date) {
			return 10 <= date.length() ? date.substring(8, 10) : "";
		}

	};
	public static final UnixtimeTransform HOUR = new UnixtimeTransform(Calendar.HOUR_OF_DAY) {

		@Override
		String extract(String date) {
			return 13 <= date.length() ? date.substring(11, 13) : "";
		}

	};
	public static final UnixtimeTransform MINUTE = new UnixtimeTransform(Calendar.MINUTE) {

		@Override
		String extract(String date) {
			return 16 <= date.length() ? date.substring(14, 16) : "";
		}

	};
	public static final UnixtimeTransform SECOND = new UnixtimeTransform(Calendar.SECOND) {

		@Override
		String extract(String date) {
			return 19 <= date.length() ? date.substring(17, 19) : "";
		}

	};
	public static final UnixtimeTransform MILLISECOND = new UnixtimeTransform(Calendar.MILLISECOND) {

		@Override
		String extract(String date) {
			return 23 <= date.length() ? date.substring(20, 23) : "";
		}

	};

	private static final List<UnixtimeTransform> TRANSFORMS = Arrays.asList(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, MILLISECOND);

	private int field;

	private UnixtimeTransform(int field) {
		this.field = field;
	}

	abstract String extract(String date);

	public Unixtime apply(Unixtime unixtime, String date) {
		date = extract(date);
		if (!date.isEmpty()) {
			unixtime = unixtime.field(field, Integer.parseInt(date));
		} else {
			unixtime = new Unixtime(unixtime.getTimeInMillis());
		}
		return unixtime;
	}

	public static Unixtime applyAll(Unixtime unixtime, String date) {
		for (UnixtimeTransform transform : UnixtimeTransform.TRANSFORMS) {
			unixtime = transform.apply(unixtime, date);
		}
		return new Unixtime(unixtime.getTimeInMillis());
	}

}
