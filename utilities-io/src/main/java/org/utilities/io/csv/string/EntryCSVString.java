package org.utilities.io.csv.string;

import java.util.Map;

import org.utilities.core.dataframe.MapDataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;

public class EntryCSVString<I> extends MapDataEntry {

	private I metainfo;

	public EntryCSVString(I metainfo) {
		this.metainfo = metainfo;
	}

	public EntryCSVString(I metainfo, Map<String, String> cells) {
		this.metainfo = metainfo;
		cells.entrySet()
				.stream()
				.forEach(this::put);
	}

	public EntryCSVString<I> put(Map.Entry<String, String> entry) {
		put(entry.getKey(), entry.getValue());
		return this;
	}

	public String getString(String label) {
		return get(label).stringValue();
	}

	public I getMetainfo() {
		return metainfo;
	}

	@Override
	public String toString() {
		return "EntryCSVString [metainfo=" + metainfo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metainfo == null) ? 0 : metainfo.hashCode());
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
		EntryCSVString<?> other = (EntryCSVString<?>) obj;
		if (metainfo == null) {
			if (other.metainfo != null)
				return false;
		} else if (!metainfo.equals(other.metainfo))
			return false;
		return super.equals(obj);
	}

}