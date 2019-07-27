package org.utilities.dataframe.datavalue;

import java.util.Date;

public class BooleanDataValue implements DataValue {

	private Boolean value;

	public BooleanDataValue(Boolean value) {
		this.value = value;
	}

	@Override
	public String stringValue() {
		return value != null ? value.toString() : null;
	}

	@Override
	public Integer intValue() {
		return value != null ? (value ? 1 : 0) : null;
	}

	@Override
	public Long longValue() {
		return value != null ? (value ? 1l : 0l) : null;
	}

	@Override
	public Float floatValue() {
		return value != null ? (value ? 1f : 0f) : null;
	}

	@Override
	public Double doubleValue() {
		return value != null ? (value ? 1d : 0d) : null;
	}

	@Override
	public Date dateValue() {
		throw new Error();
	}

	@Override
	public Boolean booleanValue() {
		return value;
	}

	@Override
	public int compareTo(DataValue other) {
		return this.value.compareTo(other.booleanValue());
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
		BooleanDataValue other = (BooleanDataValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}