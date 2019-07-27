package org.utilities.dataframe.dataentry;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.dataframe.datavalue.DateDataValue;
import org.utilities.dataframe.datavalue.DoubleDataValue;
import org.utilities.dataframe.datavalue.FloatDataValue;
import org.utilities.dataframe.datavalue.IntDataValue;
import org.utilities.dataframe.datavalue.LongDataValue;
import org.utilities.dataframe.datavalue.StringDataValue;

public class DataEntryImpl implements DataEntry, Iterable<Map.Entry<String, DataValue>> {

	private Map<String, DataValue> values = new HashMap<>();

	public DataEntryImpl() {
	}

	public DataEntryImpl(DataEntry entry) {
		entry.keys()
				.forEach(key -> put(key, entry.get(key)));
	}

	public DataEntryImpl put(String name, DataValue value) {
		values.put(name, value);
		return this;
	}

	public DataEntryImpl putAll(DataEntry entry) {
		for (String label : entry.keys()) {
			DataValue value = entry.get(label);
			if (value != null) {
				put(label, value);
			}
		}
		return this;
	}

	public DataEntryImpl putAll(DataEntryImpl entry) {
		this.values.putAll(entry.values);
		return this;
	}

	public DataEntryImpl put(String name, String value) {
		put(name, new StringDataValue(value));
		return this;
	}

	public DataEntryImpl put(String name, Integer value) {
		put(name, new IntDataValue(value));
		return this;
	}

	public DataEntryImpl put(String name, Long value) {
		put(name, new LongDataValue(value));
		return this;
	}

	public DataEntryImpl put(String name, Float value) {
		put(name, new FloatDataValue(value));
		return this;
	}

	public DataEntryImpl put(String name, Double value) {
		put(name, new DoubleDataValue(value));
		return this;
	}

	public DataEntryImpl put(String name, Date value) {
		put(name, new DateDataValue(value));
		return this;
	}

	@Override
	public Set<String> keys() {
		return values.keySet();
	}

	@Override
	public DataValue get(String name) {
		return values.get(name);
	}

	@Override
	public Collection<DataValue> values() {
		return values.values();
	}

	@Override
	public Iterator<Map.Entry<String, DataValue>> iterator() {
		return values.entrySet()
				.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		DataEntryImpl other = (DataEntryImpl) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataEntryImpl [values=" + values + "]";
	}

}
