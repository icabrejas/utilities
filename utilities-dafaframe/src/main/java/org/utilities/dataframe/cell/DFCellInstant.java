package org.utilities.dataframe.cell;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Locale;

import org.utilities.core.UtilitiesTime;

public class DFCellInstant implements DFCell {

	private Instant value;
	private String formatPattern = UtilitiesTime.DATE_IN_MILLIS_PATTERN;
	private String timeZoneID = UtilitiesTime.DEFAULT_TIMEZONE;
	private Locale locale = UtilitiesTime.DEFAULT_LOCALE;

	public DFCellInstant(Instant value) {
		if (value == null) {
			throw new Error();
		}
		this.value = value;
	}

	public DFCellInstant formatPattern(String formatPattern) {
		this.formatPattern = formatPattern;
		return this;
	}

	public DFCellInstant timeZoneID(String timeZoneID) {
		this.timeZoneID = timeZoneID;
		return this;
	}

	public DFCellInstant locale(Locale locale) {
		this.locale = locale;
		return this;
	}

	// FIXME
	@Override
	public String stringValue() {
		DateFormat format = UtilitiesTime.getFormatter(formatPattern, locale, timeZoneID);
		return value != null ? format.format(value) : null;
	}

	@Override
	public Integer intValue() {
		throw new Error();
	}

	@Override
	public Long longValue() {
		return value.toEpochMilli();
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
	public Instant instantValue() {
		return value;
	}

	@Override
	public Boolean booleanValue() {
		throw new Error();
	}

	@Override
	public int compareTo(DFCell other) {
		return this.value.compareTo(other.instantValue());
	}

	@Override
	public String toString() {
		return "DFCellInstant [value=" + value + ", formatPattern=" + formatPattern + ", timeZoneID=" + timeZoneID
				+ ", locale=" + locale + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		DFCellInstant other = (DFCellInstant) obj;
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
