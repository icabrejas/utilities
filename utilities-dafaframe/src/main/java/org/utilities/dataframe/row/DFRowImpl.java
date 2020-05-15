package org.utilities.dataframe.row;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.utilities.dataframe.cell.DFCell;
import org.utilities.dataframe.cell.DFCellDouble;
import org.utilities.dataframe.cell.DFCellFloat;
import org.utilities.dataframe.cell.DFCellInstant;
import org.utilities.dataframe.cell.DFCellInt;
import org.utilities.dataframe.cell.DFCellLong;
import org.utilities.dataframe.cell.DFCellString;

public class DFRowImpl implements DFRow {

	private Map<String, DFCell> cells = new HashMap<>();

	public DFRowImpl() {
	}

	public DFRowImpl(String name, DFCell value) {
		cells.put(name, value);
	}

	public DFRowImpl(DFRow row) {
		putAll(row);
	}

	public DFRowImpl put(String name, DFCell value) {
		cells.put(name, value);
		return this;
	}

	public DFRowImpl put(Map.Entry<String, DFCell> entry) {
		put(entry.getKey(), entry.getValue());
		return this;
	}

	public DFRowImpl putAll(DFRow row) {
		row.forEach(this::put);
		return this;
	}

	public DFRowImpl putAll(DFRowImpl entry) {
		this.cells.putAll(entry.cells);
		return this;
	}

	public DFRowImpl put(String name, String value) {
		put(name, new DFCellString(value));
		return this;
	}

	public DFRowImpl put(String name, Integer value) {
		put(name, new DFCellInt(value));
		return this;
	}

	public DFRowImpl put(String name, Long value) {
		put(name, new DFCellLong(value));
		return this;
	}

	public DFRowImpl put(String name, Float value) {
		put(name, new DFCellFloat(value));
		return this;
	}

	public DFRowImpl put(String name, Double value) {
		put(name, new DFCellDouble(value));
		return this;
	}

	public DFRowImpl put(String name, Instant value) {
		put(name, new DFCellInstant(value));
		return this;
	}

	@Override
	public Set<String> keys() {
		return cells.keySet();
	}

	@Override
	public DFCell get(String name) {
		return cells.get(name);
	}

	@Override
	public Collection<DFCell> values() {
		return cells.values();
	}

	@Override
	public Iterator<Map.Entry<String, DFCell>> iterator() {
		return cells.entrySet()
				.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
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
		DFRowImpl other = (DFRowImpl) obj;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataEntryImpl [values=" + cells + "]";
	}

}
