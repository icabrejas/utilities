package org.utilities.core.dataframe.entry.value;

import java.util.Date;

public class IntDataValue implements DataValue {

	private Integer value;

	public IntDataValue(Integer value) {
		this.value = value;
	}

	@Override
	public String stringValue() {
		return value != null ? value.toString() : null;
	}

	@Override
	public Integer intValue() {
		return value;
	}

	@Override
	public Long longValue() {
		return value != null ? value.longValue() : null;
	}

	@Override
	public Float floatValue() {
		return value != null ? value.floatValue() : null;
	}

	@Override
	public Double doubleValue() {
		return value != null ? value.doubleValue() : null;
	}

	@Override
	public Date dateValue() {
		throw new Error();
	}

	@Override
	public int compareTo(DataValue other) {
		return this.value.compareTo(other.intValue());
	}

	@Override
	public String toString() {
		return "Int [value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		IntDataValue other = (IntDataValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}