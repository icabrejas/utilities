package org.utilities.core.lang.iterable.timeseries.summary;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.utilities.core.lang.iterable.Entry;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.timeseries.Event;
import org.utilities.core.time.Unixtime;
import org.utilities.core.util.function.BiFunctionPlus;
import org.utilities.core.util.map.NotNullMap;

public interface Summary<I, V> extends Function<List<Event<I, V>>, Event<I, V>> {

	public static class ByColumn<I, V> implements Summary<I, V> {

		private Function<Iterable<I>, I> metainfo;
		private Function<Iterable<Unixtime>, Unixtime> unixtime;
		private Function<Iterable<V>, V> summary;

		public ByColumn(Function<Iterable<I>, I> metainfo, Function<Iterable<Unixtime>, Unixtime> unixtime, Function<Iterable<V>, V> summary) {
			this.metainfo = metainfo;
			this.unixtime = unixtime;
			this.summary = summary;
		}

		public ByColumn(long window, Function<Iterable<V>, V> summary) {
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
		public Event<I, V> apply(List<Event<I, V>> events) {
			I metainfo = metainfo(events);
			Unixtime unixtime = unixtime(events);
			Event<I, V> summary = new Event<>(metainfo, unixtime);
			summary.setValues(summarize(events));
			return summary;
		}

		private I metainfo(List<Event<I, V>> events) {
			return IterablePipe.newInstance(events)
					.map(Event::getMetainfo)
					.apply(this.metainfo);
		}

		private Unixtime unixtime(List<Event<I, V>> events) {
			return IterablePipe.newInstance(events)
					.map(Event::getUnixtime)
					.apply(this.unixtime);
		}

		private Map<String, V> summarize(Iterable<Event<I, V>> events) {
			NotNullMap<String, List<V>> rawValues = new NotNullMap<>(ArrayList::new);
			for (Event<I, V> evt : events) {
				for (Entry<String, V> value : evt) {
					rawValues.get(value.getInfo())
							.add(value.getContent());
				}
			}
			Map<String, V> summaryValues = new HashMap<>();
			for (String label : rawValues.keySet()) {
				summaryValues.put(label, summary.apply(rawValues.get(label)));
			}
			return summaryValues;
		}

	}

}
