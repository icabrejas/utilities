package org.utilities.dataframe.cell;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class DFCellString implements DFCell {

	private String value;
	private DateTimeFormatter format;

	public DFCellString(String value) {
		if (value == null) {
			throw new Error();
		}
		this.value = value;
	}

	public DFCellString format(DateTimeFormatter format) {
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
	public Instant instantValue() {
		if (format != null) {
			return value != null ? Instant.from(format.parse(value)) : null;
		} else {
			throw new Error();
		}
	}

	@Override
	public Boolean booleanValue() {
		return (value != null && !value.isEmpty()) ? Boolean.parseBoolean(value) : null;
	}

	@Override
	public int compareTo(DFCell other) {
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
		DFCellString other = (DFCellString) obj;
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