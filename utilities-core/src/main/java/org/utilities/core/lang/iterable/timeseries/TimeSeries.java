package org.utilities.core.lang.iterable.timeseries;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.IterablePipeInterwined;
import org.utilities.core.lang.iterable.IterablePipeTracker;
import org.utilities.core.lang.iterable.timeseries.filters.EventFilterTime;
import org.utilities.core.lang.iterable.timeseries.filters.EventFilterValue;
import org.utilities.core.lang.iterable.timeseries.summary.Summary;
import org.utilities.core.time.UtilitiesTime;

//TODO validate key and time while iteration
public interface TimeSeries<I, V extends Comparable<V>> extends IterablePipe<Event<I, V>> {

	public static <I, V extends Comparable<V>> TimeSeries<I, V> newInstance(Iterable<Event<I, V>> events) {
		return events::iterator;
	}

	default TimeSeries<I, V> filerNotNull(String... labels) {
		IterablePipe<Event<I, V>> it = this;
		for (String label : labels) {
			it = filter(EventFilterValue.isNotNull(label));
		}
		return it::iterator;
	}

	default TimeSeries<I, V> filterTime(String date) throws QuietException {
		IterablePipe<Event<I, V>> it = this;
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

	default TimeSeries<I, V> batch(long window, Function<List<Event<I, V>>, Event<I, V>> summary) {
		return this.batch(IterablePipeTracker.Tracker.Predicate.interval(Event::getTimeInUnix, window))
				.map(summary)
				.apply(TimeSeries::newInstance);
	}

	default TimeSeries<I, V> batchByColumn(long window, Function<Iterable<V>, V> summary) {
		return batch(window, new Summary.ByColumn<>(window, summary));
	}

	public static TimeSeries<String, Double> bindColumns(Iterable<TimeSeries<String, Double>> series, String metainfo) {
		Comparator<Event<String, Double>> comparator = Event.byTimeComparator(String.class, Double.class)
				.thenComparing(Event.byInfoComparator());
		return IterablePipeInterwined.newInstance(comparator, series)
				.batch(Event::timeEquals)
				.map(Event::bind, metainfo)::iterator;
	}

}
