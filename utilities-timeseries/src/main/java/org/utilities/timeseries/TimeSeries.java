package org.utilities.timeseries;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.observer.IterablePipeObserved;
import org.utilities.core.lang.iterable.weave.IterablePipeWoven;
import org.utilities.core.time.UtilitiesTime;
import org.utilities.timeseries.filter.EventFilterTime;
import org.utilities.timeseries.filter.EventFilterValue;
import org.utilities.timeseries.summary.Summary;

public class TimeSeries<I> implements IterablePipe<Event<I>> {

	private Iterable<Event<I>> events;

	private TimeSeries(Iterable<Event<I>> events) {
		this.events = events;
	}

	public static <I, V> TimeSeries<I> newInstance(Iterable<Event<I>> events) {
		events = IterablePipeObserved.from(events, new TimeSeriesValidator<>());
		return new TimeSeries<>(events);
	}

	@Override
	public Iterator<Event<I>> iterator() {
		return events.iterator();
	}

	public TimeSeries<I> filerNotNull(String... labels) {
		IterablePipe<Event<I>> it = this;
		for (String label : labels) {
			it = filter(EventFilterValue.isNotNull(label));
		}
		return new TimeSeries<>(it);
	}

	public TimeSeries<I> filterTime(String date) throws QuietException {
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
		return new TimeSeries<>(it);
	}

	public <W> TimeSeries<I> batch(long window, Function<List<Event<I>>, Event<I>> summary) {
		IterablePipe<Event<I>> events = this.interval(Event::getTimeInUnix, window)
				.map(summary);
		return new TimeSeries<>(events);
	}

	public <W> TimeSeries<I> batchByColumn(long window, Function<Iterable<DataValue>, DataValue> summary) {
		return batch(window, new Summary.ByColumn<>(window, summary));
	}

	public <W> TimeSeries<I> rollBatch(long window, Function<List<Event<I>>, Event<I>> summary) {
		IterablePipe<Event<I>> events = this.rollInterval(Event::getTimeInUnix, window)
				.map(summary);
		return new TimeSeries<>(events);
	}

	public <W> TimeSeries<I> rollBatchByColumn(long window, Function<Iterable<DataValue>, DataValue> summary) {
		return rollBatch(window, new Summary.ByColumn<>(window, summary));
	}

	public static TimeSeries<String> bindColumns(Iterable<TimeSeries<String>> series, String metainfo) {
		Comparator<Event<String>> comparator = Event.timeComparator(String.class)
				.thenComparing(Event.infoComparator(String.class));
		IterablePipe<Event<String>> events = IterablePipeWoven.from(comparator, series)
				.batch(Event::timeEquals)
				.flatMap(Event::bind, metainfo);
		return new TimeSeries<>(events);
	}

}
