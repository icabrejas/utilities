package org.utilities.core.dataframe.entry.value;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.utilities.core.lang.exception.QuietException;

public class StringDataValue implements DataValue {

	private String value;
	private DateFormat format;

	public StringDataValue(String value) {
		this.value = value;
	}

	public StringDataValue format(DateFormat format) {
		this.format = format;
		return this;
	}

	@Override
	public String stringValue() {
		return value;
	}

	@Override
	public Integer intValue() {
		return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : null;
	}

	@Override
	public Long longValue() {
		return (value != null && !value.isEmpty()) ? Long.parseLong(value) : null;
	}

	@Override
	public Float floatValue() {
		return (value != null && !value.isEmpty()) ? Float.parseFloat(value) : null;
	}

	@Override
	public Double doubleValue() {
		return (value != null && !value.isEmpty()) ? Double.parseDouble(value) : null;
	}

	@Override
	public Date dateValue() {
		if (format != null) {
			try {
				return value != null ? format.parse(value) : null;
			} catch (ParseException e) {
				throw new QuietException(e);
			}
		} else {
			throw new Error();
		}
	}

	@Override
	public int compareTo(DataValue other) {
		return this.value.compareTo(other.stringValue());
	}

	@Override
	public String toString() {
		return "String [value=" + value + ", format=" + format + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((format == null) ? 0 : format.hashCode());
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
		StringDataValue other = (StringDataValue) obj;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}