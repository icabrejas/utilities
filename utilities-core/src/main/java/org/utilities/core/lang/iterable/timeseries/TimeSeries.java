package org.utilities.core.lang.iterable.timeseries;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.IterablePipeInterwined;
import org.utilities.core.lang.iterable.IterablePipeTracker;
import org.utilities.core.lang.iterable.timeseries.filters.EventFilterTime;
import org.utilities.core.lang.iterable.timeseries.filters.EventFilterValue;
import org.utilities.core.lang.iterable.timeseries.summary.Summary;
import org.utilities.core.time.UtilitiesTime;

//TODO validate key and time while iteration
public interface TimeSeries<I> extends IterablePipe<Event<I>> {

	public static <I, V> TimeSeries<I> newInstance(Iterable<Event<I>> events) {
		return events::iterator;
	}

	default TimeSeries<I> filerNotNull(String... labels) {
		IterablePipe<Event<I>> it = this;
		for (String label : labels) {
			it = filter(EventFilterValue.isNotNull(label));
		}
		return it::iterator;
	}

	default TimeSeries<I> filterTime(String date) throws QuietException {
		IterablePipe<Event<I>> it = this;
		int index = date.indexOf('/');
		if (index < 0) {
			// TODO
			throw new RuntimeException("Not implemented yet");
		} else {
			long from = UtilitiesTime.parseQuietly(date.substring(0, index))
					.getTimeInUnix();
			long to = UtilitiesTime.parseQuietly(date.substring(index + 1))
					.getTimeInUnix();
			it = filter(EventFilterTime.isBetween(from, to));

		}
		return it::iterator;
	}

	default <W> TimeSeries<I> batch(long window, Function<List<Event<I>>, Event<I>> summary) {
		return this.batch(IterablePipeTracker.Tracker.Predicate.interval(Event::getTimeInUnix, window))
				.map(summary)
				.apply(TimeSeries::newInstance);
	}

	default <W> TimeSeries<I> batchByColumn(long window, Function<Iterable<DataValue>, DataValue> summary) {
		return batch(window, new Summary.ByColumn<>(window, summary));
	}

	public static TimeSeries<String> bindColumns(Iterable<TimeSeries<String>> series, String metainfo) {
		Comparator<Event<String>> comparator = Event.timeComparator(String.class)
				.thenComparing(Event.infoComparator(String.class));
		return IterablePipeInterwined.newInstance(comparator, series)
				.batch(Event::timeEquals)
				.flatMap(Event::bind, metainfo)::iterator;
	}

}
