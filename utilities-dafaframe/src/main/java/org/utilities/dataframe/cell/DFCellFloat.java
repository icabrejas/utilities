package org.utilities.dataframe.cell;

import java.time.Instant;

public class DFCellFloat implements DFCell {

	private Float value;

	public DFCellFloat(Float value) {
		if (value == null) {
			throw new Error();
		}
		this.value = value;
	}

	@Override
	public String stringValue() {
		return value != null ? value.toString() : null;
	}

	@Override
	public Integer intValue() {
		return value != null ? value.intValue() : null;
	}

	@Override
	public Long longValue() {
		return value != null ? value.longValue() : null;
	}

	@Override
	public Float floatValue() {
		return value;
	}

	@Override
	public Double doubleValue() {
		return value != null ? value.doubleValue() : null;
	}

	@Override
	public Instant instantValue() {
		throw new Error();
	}

	@Override
	public Boolean booleanValue() {
		if (value == null) {
			return null;
		} else if (value == 0) {
			return false;
		} else if (value == 1) {
			return true;
		} else {
			throw new Error();
		}
	}

	@Override
	public int compareTo(DFCell other) {
		return this.value.compareTo(other.floatValue());
	}

	@Override
	public String toString() {
		return "Float [value=" + value + "]";
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
		DFCellFloat other = (DFCellFloat) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}