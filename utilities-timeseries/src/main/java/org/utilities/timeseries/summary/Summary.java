package org.utilities.timeseries.summary;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.map.NotNullMap;
import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.dataentry.DataEntryImpl;
import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.timeseries.Event;

public interface Summary<I> extends Function<List<Event>, Event> {

	public static class ByColumn<I> implements Summary<I> {

		private Function<Iterable<Instant>, Instant> instant;
		private Function<Iterable<DataValue>, DataValue> summary;

		public ByColumn(Function<Iterable<Instant>, Instant> unixtime,
				Function<Iterable<DataValue>, DataValue> summary) {
			this.instant = unixtime;
			this.summary = summary;
		}

		public ByColumn(long window, Function<Iterable<DataValue>, DataValue> summary) {
			this.instant = BiFunctionPlus.parseFunction(Summary.ByColumn::unixtime, window);
			this.summary = summary;
		}

		private static Instant unixtime(Iterable<Instant> times, long window) {
			Instant unixtime = null;
			for (Instant time : times) {
				long end = intervalEnd(window, time);
				if (unixtime == null) {
					unixtime = Instant.ofEpochMilli(end);
				} else if (end != unixtime.toEpochMilli()) {
					throw new InvalidParameterException();
				}
			}
			return unixtime;
		}

		private static long intervalEnd(long window, Instant time) {
			long start = time.toEpochMilli() - time.toEpochMilli() % window;
			if (start == time.toEpochMilli()) {
				start -= window;
			}
			return start + window;
		}

		@Override
		public Event apply(List<Event> events) {
			Instant unixtime = unixtime(events);
			Event summary = new Event(unixtime);
			summary.putAll(summarize(events));
			return summary;
		}

		private Instant unixtime(List<Event> events) {
			return IterablePipe.newInstance(events)
					.map(Event::getTime)
					.apply(this.instant);
		}

		private DataEntry summarize(Iterable<Event> events) {
			NotNullMap<String, List<DataValue>> rawValues = new NotNullMap<>(ArrayList::new);
			for (Event evt : events) {
				for (String name : evt.keys()) {
					DataValue value = evt.get(name);
					rawValues.get(name)
							.add(value);
				}
			}
			DataEntryImpl summaryValues = new DataEntryImpl();
			for (String label : rawValues.keySet()) {
				summaryValues.put(label, summary.apply(rawValues.get(label)));
			}
			return summaryValues;
		}

	}

}
