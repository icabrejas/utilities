package org.utilities.core.dataframe.entry;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.dataframe.entry.value.DateDataValue;
import org.utilities.core.dataframe.entry.value.DoubleDataValue;
import org.utilities.core.dataframe.entry.value.FloatDataValue;
import org.utilities.core.dataframe.entry.value.IntDataValue;
import org.utilities.core.dataframe.entry.value.LongDataValue;
import org.utilities.core.dataframe.entry.value.StringDataValue;

public class DataEntryImpl implements DataEntry, Iterable<Map.Entry<String, DataValue>> {

	private Map<String, DataValue> values = new HashMap<>();

	// public DataMap putAll(DataMap dataMap) {
	// return putAll(dataMap.values);
	// }
	//
	// public DataMap putAll(Map<? extends String, ? extends DataValue> values)
	// {
	// this.values.putAll(values);
	// return this;
	// }
	//
	// public DataMap putAll(DataEntry values) {
	// for (String name : values.names()) {
	// put(name, values.get(name));
	// }
	// return this;
	// }
	//
	public DataEntryImpl put(String name, DataValue value) {
		values.put(name, value);
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
	public String toString() {
		return "DataMap [values=" + values + "]";
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

}
