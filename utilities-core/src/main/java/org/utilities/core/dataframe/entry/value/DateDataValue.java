package org.utilities.core.dataframe.entry.value;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.utilities.core.time.UtilitiesTime;

public class DateDataValue implements DataValue {

	private Date value;
	private String formatPattern = UtilitiesTime.DEFAULT_PATTERN;
	private Integer field = Calendar.YEAR;
	private String timeZoneID = UtilitiesTime.DEFAULT_TIMEZONE;
	private Locale locale = UtilitiesTime.DEFAULT_LOCALE;

	public DateDataValue(Date value) {
		this.value = value;
	}

	public DateDataValue formatPattern(String formatPattern) {
		this.formatPattern = formatPattern;
		return this;
	}

	public DateDataValue field(Integer field) {
		this.field = field;
		return this;
	}

	public DateDataValue timeZoneID(String timeZoneID) {
		this.timeZoneID = timeZoneID;
		return this;
	}

	public DateDataValue locale(Locale locale) {
		this.locale = locale;
		return this;
	}

	@Override
	public String stringValue() {
		DateFormat format = UtilitiesTime.getFormatter(formatPattern, locale, timeZoneID);
		return value != null ? format.format(value) : null;
	}

	@Override
	public Integer intValue() {
		return UtilitiesTime.get(value, field, timeZoneID, locale);
	}

	@Override
	public Long longValue() {
		return value.getTime();
	}

	@Override
	public Float floatValue() {
		throw new Error();
	}

	@Override
	public Double doubleValue() {
		throw new Error();
	}

	@Override
	public Date dateValue() {
		return value;
	}

	@Override
	public DateDataValue add(int field, int amount) {
		return new DateDataValue(UtilitiesTime.add(value, field, amount, timeZoneID, locale));
	}

	@Override
	public int compareTo(DataValue other) {
		return this.value.compareTo(other.dateValue());
	}

	@Override
	public String toString() {
		return "Date [value=" + value + ", formatPattern=" + formatPattern + ", field=" + field + ", timeZoneID="
				+ timeZoneID + ", locale=" + locale + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((formatPattern == null) ? 0 : formatPattern.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((timeZoneID == null) ? 0 : timeZoneID.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateDataValue other = (DateDataValue) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (formatPattern == null) {
			if (other.formatPattern != null)
				return false;
		} else if (!formatPattern.equals(other.formatPattern))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (timeZoneID == null) {
			if (other.timeZoneID != null)
				return false;
		} else if (!timeZoneID.equals(other.timeZoneID))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
