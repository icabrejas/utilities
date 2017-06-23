package org.utilities.io.csv.string;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.time.Unixtime;
import org.utilities.core.time.UtilitiesTime;
import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.function.PredicatePlus;

public class EntryCSVString<I> {

	private I metainfo;
	private Map<String, String> cells = new HashMap<>();

	public EntryCSVString(I metainfo, Map<String, String> cells) {
		this.metainfo = metainfo;
		this.cells = cells;
	}

	public EntryCSVString(I metainfo) {
		this.metainfo = metainfo;
	}

	public EntryCSVString<I> put(String header, String field) {
		cells.put(header, field);
		return this;
	}

	public I getMetainfo() {
		return metainfo;
	}

	public Set<String> getHeaders() {
		return cells.keySet();
	}

	public Collection<String> getFields() {
		return cells.values();
	}

	public int length() {
		return cells.size();
	}

	public <T> T get(String label, Function<String, T> parser) {
		String value = getString(label);
		return (value == null || value.isEmpty()) ? null : parser.apply(value);
	}

	public String getString(String label) {
		return cells.get(label);
	}

	public Integer getInt(String label) {
		return get(label, Integer::parseInt);
	}

	public Long getLong(String label) {
		return get(label, Long::parseLong);
	}

	public Float getFloat(String label) {
		return get(label, Float::parseFloat);
	}

	public Double getDouble(String label) {
		return get(label, Double::parseDouble);
	}

	public Unixtime getUnixtime(String label) throws QuietException {
		return get(label, UtilitiesTime::parseQuietly);
	}

	public Unixtime getUnixtime(String label, String pattern) throws QuietException {
		return get(label, BiFunctionPlus.parseFunction(UtilitiesTime::parseQuietly, pattern));
	}

	public Date getDate(String label) throws QuietException {
		return get(label, UtilitiesTime::parseQuietly).getTimeInDate();
	}

	public Date getDate(String label, String pattern) throws QuietException {
		return get(label, BiFunctionPlus.parseFunction(UtilitiesTime::parseQuietly, pattern)).getTimeInDate();
	}

	public EntryCSVString<I> select(Iterable<String> labels) {
		EntryCSVString<I> selected = new EntryCSVString<>(metainfo);
		for (String label : labels) {
			selected.put(label, getString(label));
		}
		return selected;
	}

	public Iterable<EntryCSVString<I>> gather(Iterable<String> labels, String key, String value, String... notGather) {
		return gather(labels, key, value, Arrays.asList(notGather));
	}

	public Iterable<EntryCSVString<I>> gather(Iterable<String> labels, String key, String value,
			List<String> notGather) {
		return IterablePipe.newInstance(getHeaders())
				.filter(PredicatePlus.negate(notGather::contains))
				.map(label -> getEntry(notGather, key, value, label));
	}

	private EntryCSVString<I> getEntry(List<String> notGather, String key, String value, String label) {
		return this.select(notGather)
				.put(key, label)
				.put(value, getString(label));
	}

	@Override
	public String toString() {
		return "EntryCSV [metainfo=" + metainfo + ", cells=" + cells + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
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
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		if (metainfo == null) {
			if (other.metainfo != null)
				return false;
		} else if (!metainfo.equals(other.metainfo))
			return false;
		return true;
	}

}