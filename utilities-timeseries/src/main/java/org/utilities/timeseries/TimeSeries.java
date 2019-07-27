package org.utilities.timeseries;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.observer.IterablePipeObserved;
import org.utilities.core.time.UtilitiesTime;
import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.dataframe.filter.DataValueFilter;
import org.utilities.timeseries.filter.EventTimeFilter;
import org.utilities.timeseries.summary.Summary;

public class TimeSeries implements IterablePipe<Event> {

	private Iterable<Event> events;

	private TimeSeries(Iterable<Event> events) {
		this.events = events;
	}

	public static <V> TimeSeries newInstance(Iterable<Event> events) {
		events = new IterablePipeObserved<>(events, new TimeSeriesValidator());
		return new TimeSeries(events);
	}

	@Override
	public Iterator<Event> iterator() {
		return events.iterator();
	}

	public TimeSeries filerNotNull(String... names) {
		IterablePipe<Event> it = this;
		for (String name : names) {
			it = filter(DataValueFilter.isNull(name));
		}
		return new TimeSeries(it);
	}

	public TimeSeries filterTime(String date) throws QuietException {
		IterablePipe<Event> it = this;
		int index = date.indexOf('/');
		if (index < 0) {
			// TODO
			throw new RuntimeException("Not implemented yet");
		} else {
			Instant from = UtilitiesTime.parseQuietly(date.substring(0, index));
			Instant to = UtilitiesTime.parseQuietly(date.substring(index + 1));
			it = filter(EventTimeFilter.isBetween(from, to));

		}
		return new TimeSeries(it);
	}

	public <W> TimeSeries batch(long window, Function<List<Event>, Event> summary) {
		IterablePipe<Event> events = this.interval(Event::getTimeInUnix, window)
				.map(summary);
		return new TimeSeries(events);
	}

	public <W> TimeSeries batchByColumn(long window, Function<Iterable<DataValue>, DataValue> summary) {
		return batch(window, new Summary.ByColumn<>(window, summary));
	}

	public <W> TimeSeries rollBatch(long window, Function<List<Event>, Event> summary) {
		IterablePipe<Event> events = this.rollInterval(Event::getTimeInUnix, window)
				.map(summary);
		return new TimeSeries(events);
	}

	public <W> TimeSeries rollBatchByColumn(long window, Function<Iterable<DataValue>, DataValue> summary) {
		return rollBatch(window, new Summary.ByColumn<>(window, summary));
	}

}
