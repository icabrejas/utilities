package org.utilities.io.csv.string;

import java.util.Map;

import org.utilities.core.dataframe.entry.DataEntryImpl;

public class EntryCSVString<I> extends DataEntryImpl {

	private I metadata;

	public EntryCSVString(I metadata) {
		this.metadata = metadata;
	}

	public EntryCSVString(I metadata, Map<String, String> cells) {
		this.metadata = metadata;
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

	public I getMetadata() {
		return metadata;
	}

	@Override
	public String toString() {
		return "EntryCSVString [metadata=" + metadata + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
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
		if (metadata == null) {
			if (other.metadata != null)
				return false;
		} else if (!metadata.equals(other.metadata))
			return false;
		return super.equals(obj);
	}

}