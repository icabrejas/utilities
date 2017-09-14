package org.utilities.core.lang.iterable.timeseries.summary;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.utilities.core.dataframe.MapDataEntry;
import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.timeseries.Event;
import org.utilities.core.time.Unixtime;
import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.map.NotNullMap;

public interface Summary<I> extends Function<List<Event<I>>, Event<I>> {

	public static class ByColumn<I> implements Summary<I> {

		private Function<Iterable<I>, I> metainfo;
		private Function<Iterable<Unixtime>, Unixtime> unixtime;
		private Function<Iterable<DataValue>, DataValue> summary;

		public ByColumn(Function<Iterable<I>, I> metainfo, Function<Iterable<Unixtime>, Unixtime> unixtime,
				Function<Iterable<DataValue>, DataValue> summary) {
			this.metainfo = metainfo;
			this.unixtime = unixtime;
			this.summary = summary;
		}

		public ByColumn(long window, Function<Iterable<DataValue>, DataValue> summary) {
			this.metainfo = Summary.ByColumn::metainfo;
			this.unixtime = BiFunctionPlus.parseFunction(Summary.ByColumn::unixtime, window);
			this.summary = summary;
		}

		private static <I> I metainfo(Iterable<I> infos) {
			I commonInfo = null;
			for (I info : infos) {
				if (commonInfo == null) {
					commonInfo = info;
				} else if (!commonInfo.equals(info)) {
					throw new InvalidParameterException();
				}
			}
			return commonInfo;
		}

		private static Unixtime unixtime(Iterable<Unixtime> times, long window) {
			Unixtime unixtime = null;
			for (Unixtime time : times) {
				long end = intervalEnd(window, time);
				if (unixtime == null) {
					unixtime = new Unixtime(end);
				} else if (end != unixtime.getTimeInMillis()) {
					throw new InvalidParameterException();
				}
			}
			return unixtime;
		}

		private static long intervalEnd(long window, Unixtime time) {
			long start = time.getTimeInMillis() - time.getTimeInMillis() % window;
			if (start == time.getTimeInMillis()) {
				start -= window;
			}
			return start + window;
		}

		@Override
		public Event<I> apply(List<Event<I>> events) {
			I metainfo = metainfo(events);
			Unixtime unixtime = unixtime(events);
			Event<I> summary = new Event<>(metainfo, unixtime);
			summary.values(summarize(events));
			return summary;
		}

		private I metainfo(List<Event<I>> events) {
			return IterablePipe.newInstance(events)
					.map(Event::getMetainfo)
					.apply(this.metainfo);
		}

		private Unixtime unixtime(List<Event<I>> events) {
			return IterablePipe.newInstance(events)
					.map(Event::getUnixtime)
					.apply(this.unixtime);
		}

		private DataEntry summarize(Iterable<Event<I>> events) {
			NotNullMap<String, List<DataValue>> rawValues = new NotNullMap<>(ArrayList::new);
			for (Event<I> evt : events) {
				for (String name : evt.names()) {
					DataValue value = evt.get(name);
					rawValues.get(name)
							.add(value);
				}
			}
			MapDataEntry summaryValues = new MapDataEntry();
			for (String label : rawValues.keySet()) {
				summaryValues.put(label, summary.apply(rawValues.get(label)));
			}
			return summaryValues;
		}

	}

}
