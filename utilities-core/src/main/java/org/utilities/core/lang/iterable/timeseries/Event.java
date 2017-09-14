package org.utilities.core.lang.iterable.timeseries;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.utilities.core.dataframe.MapDataEntry;
import org.utilities.core.dataframe.Mutation;
import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.time.Unixtime;

public class Event<I> implements DataEntry {

	private I metainfo;
	// TODO ¿is the correct class (maybe this class is unnecessary)?
	private Unixtime unixtime;
	private DataEntry values = new MapDataEntry();

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

	public DataEntry getValues() {
		return values;
	}

	public long getTimeInMillis() {
		return unixtime.getTimeInMillis();
	}

	public long getTimeInUnix() {
		return unixtime.getTimeInUnix();
	}

	public Event<I> values(DataEntry values) {
		this.values = values;
		return this;
	}

	@Override
	public Collection<String> names() {
		return values.names();
	}

	@Override
	public DataValue get(String name) {
		return values.get(name);
	}

	private Event<I> newInstance(DataEntry values) {
		return new Event<I>(metainfo, unixtime).values(values);
	}

	@Override
	public Event<I> remove(Collection<String> names) {
		return newInstance(values.remove(names));
	}

	@Override
	public Event<I> remove(String... names) {
		return newInstance(values.remove(names));
	}

	@Override
	public <T> Event<I> mutate(Mutation mutation) {
		return newInstance(values.mutate(mutation));
	}

	@Override
	public <T> Event<I> mutate(String name, Function<DataEntry, DataValue> func) {
		return newInstance(values.mutate(name, func));
	}

	@Override
	public <T> Event<I> mutate(String name, String x, Function<DataValue, DataValue> func) {
		return newInstance(values.mutate(name, x, func));
	}

	@Override
	public <T> Event<I> mutate(String name, String x, String y, BiFunction<DataValue, DataValue, DataValue> func) {
		return newInstance(values.mutate(name, x, y, func));
	}

	@Override
	public List<? extends DataEntry> gather(String key, String value, String... names) {
		return gather(key, value, Arrays.asList(names));
	}

	@Override
	public List<? extends DataEntry> gather(String key, String value, Collection<String> names) {
		return values.gather(key, value, names)
				.stream()
				.map(this::newInstance)
				.collect(Collectors.toList());
	}

	@Override
	public DataEntry gather(String key, String value, String name) {
		return newInstance(values.gather(key, value, name));
	}

	public static List<Event<String>> bind(Collection<Event<String>> events, String metainfo) {
		return events.stream()
				.collect(Collectors.groupingBy(Event::getUnixtime))
				.entrySet()
				.stream()
				.map(entry -> Event.bind(entry.getValue(), metainfo, entry.getKey()))
				.collect(Collectors.toList());
	}

	private static Event<String> bind(Collection<Event<String>> events, String metainfo, Unixtime unixtime) {
		Map<String, DataEntry> entries = events.stream()
				.collect(Collectors.toMap(Event::getMetainfo, Event::getValues));
		DataEntry values = DataEntry.bind(entries);
		return new Event<String>(metainfo, unixtime).values(values);
	}

	// ---------------------------------------

	// FIXME
	// public static <I, V> Event<I, V> spread(List<SimpleEvent<I, V>> events) {
	// SimpleEvent<I, V> first = events.get(0);
	// Event<I, V> evt = new Event<I, V>(first.getMetainfo(),
	// first.getUnixtime());
	// for (SimpleEvent<I, V> simpleEvent : events) {
	// evt.put(simpleEvent.getLabel(), simpleEvent.getValue());
	// }
	// return evt;
	// }

	// FIXME
	// public Event<String, V> toStringInfo() {
	// Event<String, V> evt = new Event<>(metainfo.toString(), unixtime);
	// for (String label : values.keySet()) {
	// evt.put(label, values.get(label));
	// }
	// return evt;
	// }

	public static <I extends Comparable<I>> Comparator<Event<I>> keyComparator(Class<I> info) {
		return Event.infoComparator(info)
				.thenComparing(Event.timeComparator(info));
	}

	public static <I> Comparator<Event<I>> timeComparator(Class<I> info) {
		return Comparator.comparingLong(Event::getTimeInMillis);
	}

	public static <I extends Comparable<I>> Comparator<Event<I>> infoComparator(Class<I> info) {
		return Comparator.comparing(Event::getMetainfo);
	}

	public boolean keyEquals(Event<I> evt) {
		return infoEquals(evt) && timeEquals(evt);
	}

	public boolean infoEquals(Event<I> other) {
		if (this.metainfo == null) {
			return other.metainfo == null;
		} else {
			return this.metainfo.equals(other.metainfo);
		}
	}

	public boolean timeEquals(Event<I> other) {
		if (this.unixtime == null) {
			return other.unixtime == null;
		} else {
			return this.unixtime.equals(other.unixtime);
		}
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
		Event<?> other = (Event<?>) obj;
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
		return "Event [metainfo=" + metainfo + ", unixtime=" + unixtime + ", values=" + values + "]";
	}

}
