package org.utilities.core.lang.iterable.timeseries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.utilities.core.lang.iterable.Entry;
import org.utilities.core.lang.iterable.timeseries.simple.SimpleEvent;
import org.utilities.core.time.Unixtime;
import org.utilities.core.time.UtilitiesTime;

public class Event<I, V> implements Iterable<Entry<String, V>> {

	private I metainfo;
	private Unixtime unixtime;
	private Map<String, V> values = new HashMap<>();

	public Event(I metainfo, Unixtime unixtime) {
		this.metainfo = metainfo;
		this.unixtime = unixtime;
	}

	public I getMetainfo() {
		return metainfo;
	}

	public Unixtime getUnixtime() {
		return unixtime;
	}

	public long getTimeInMillis() {
		return unixtime.getTimeInMillis();
	}

	public long getTimeInUnix() {
		return unixtime.getTimeInUnix();
	}

	public Map<String, V> getValues() {
		return values;
	}

	public void setValues(Map<String, V> values) {
		this.values = values;
	}

	public V get(String label) {
		return values.get(label);
	}

	public void put(String label, V value) {
		values.put(label, value);
	}

	public Set<String> labels() {
		return values.keySet();
	}

	public int dim() {
		return values.size();
	}

	public Event<I, V> select(String... labels) {
		Event<I, V> selected = new Event<>(metainfo, unixtime);
		for (String label : values.keySet()) {
			if (-1 < Arrays.binarySearch(labels, label)) {
				selected.put(label, values.get(label));
			}
		}
		return selected;
	}

	public Event<I, V> select(List<String> labels) {
		Event<I, V> selected = new Event<>(metainfo, unixtime);
		for (String label : values.keySet()) {
			if (labels.contains(label)) {
				selected.put(label, values.get(label));
			}
		}
		return selected;
	}

	public List<SimpleEvent<I, V>> gather() {
		List<SimpleEvent<I, V>> events = new ArrayList<>();
		for (String label : values.keySet()) {
			SimpleEvent<I, V> evt = new SimpleEvent<>();
			evt.setMetainfo(metainfo);
			evt.setUnixtime(unixtime);
			evt.setLabel(label);
			evt.setValue(values.get(label));
			events.add(evt);
		}
		return events;
	}

	@Override
	public Iterator<Entry<String, V>> iterator() {
		return values.entrySet()
				.stream()
				.map(entry -> Entry.newInstance(entry::getKey, entry::getValue))
				.iterator();
	}

	public static <I, V> Comparator<Event<I, V>> byTimeComparator() {
		return byTimeComparator();
	}

	public static <I, V> Comparator<Event<I, V>> byTimeComparator(Class<I> info, Class<V> value) {
		return Comparator.comparingLong(Event::getTimeInMillis);
	}

	public static <I extends Comparable<I>, V> Comparator<Event<I, V>> byInfoComparator() {
		return Comparator.comparing(Event::getMetainfo);
	}

	public static <I extends Comparable<I>, V> Comparator<Event<I, V>> byInfoComparator(Class<I> info, Class<V> value) {
		return byInfoComparator();
	}

	public static <I, V extends Comparable<V>> Comparator<Event<I, V>> byValueComparator() {
		return (a, b) -> {
			int comparation = 0;
			Set<String> labels = new HashSet<>();
			labels.addAll(a.values.keySet());
			labels.addAll(b.values.keySet());
			for (String label : labels) {
				V a_ = a.get(label);
				V b_ = b.get(label);
				if (a_ != null) {
					if (b_ == null) {
						return -1;
					} else {
						comparation = a_.compareTo(b_);
						if (comparation != 0) {
							return comparation;
						}
					}
				} else {
					if (b_ != null) {
						return 1;
					}
				}
			}
			return comparation;
		};
	}

	public static <I, V extends Comparable<V>> Comparator<Event<I, V>> byValueComparator(Class<I> info, Class<V> value) {
		return byValueComparator();
	}

	public boolean keyEquals(Event<I, V> evt) {
		if (!infoEquals(evt)) {
			return false;
		}
		return timeEquals(evt);
	}

	public boolean infoEquals(Event<I, V> evt) {
		if (this.metainfo == null) {
			if (evt.metainfo != null) {
				return false;
			}
		} else if (!this.metainfo.equals(evt.metainfo)) {
			return false;
		}
		return true;
	}

	public boolean timeEquals(Event<I, V> evt) {
		if (this.unixtime == null) {
			if (evt.unixtime != null) {
				return false;
			}
		} else if (!this.unixtime.equals(evt.unixtime)) {
			return false;
		}
		return true;
	}

	public static <V> Event<String, V> bind(List<Event<String, V>> events, String metainfo) {
		Event<String, V> bind = null;
		for (Event<String, V> evt : events) {
			if (bind == null) {
				bind = new Event<String, V>(metainfo, evt.unixtime);
			} else if (!bind.unixtime.equals(evt.unixtime)) {
				// FIXME define a specific exception
				throw new Error();
			}
			for (Entry<String, V> value : evt) {
				bind.put(evt.metainfo + "." + value.getInfo(), value.getContent());
			}
		}
		return bind;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metainfo == null) ? 0 : metainfo.hashCode());
		result = prime * result + ((unixtime == null) ? 0 : unixtime.hashCode());
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
		Event<?, ?> other = (Event<?, ?>) obj;
		if (metainfo == null) {
			if (other.metainfo != null)
				return false;
		} else if (!metainfo.equals(other.metainfo))
			return false;
		if (unixtime == null) {
			if (other.unixtime != null)
				return false;
		} else if (!unixtime.equals(other.unixtime))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, V> entry : values.entrySet()) {
			builder.append(entry.getKey())
					.append(": ")
					.append(entry.getValue())
					.append(", ");
		}
		if (0 < builder.length()) {
			builder.delete(builder.length() - 2, builder.length());
		}
		builder.append(']');
		return "[" + metainfo + "] " + UtilitiesTime.formatMillis(unixtime.getTimeInMillis(), "yyyy-MM-dd HH:mm:ss") + " [" + builder.toString();
	}

}
