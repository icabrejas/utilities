package org.utilities.timeseries;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.track.IPTracked;
import org.utilities.dataframe.row.DFRow;

public interface TimeSeries extends IterablePipe<Event> {

	public static TimeSeries as(Iterable<DFRow> rows, String time) {
		return IterablePipe.create(rows)
				.map(Event::new, time)
				.apply(TimeSeries::as);
	}

	public static TimeSeries as(Iterable<Event> events) {
		events = new IPTracked<>(events, TimeSeriesUtils.validator());
		return events::iterator;
	}

	// public TimeSeries filerNotNull(String... names) {
	// IterablePipe<Event> it = this;
	// for (String name : names) {
	// it = filter(DataValueFilter.isNull(name));
	// }
	// return new TimeSeries(it);
	// }
	//
	// public TimeSeries filterTime(String date) throws QuietException {
	// IterablePipe<Event> it = this;
	// int index = date.indexOf('/');
	// if (index < 0) {
	// // TODO
	// throw new RuntimeException("Not implemented yet");
	// } else {
	// Instant from = UtilitiesTime.parseQuietly(date.substring(0, index));
	// Instant to = UtilitiesTime.parseQuietly(date.substring(index + 1));
	// it = filter(EventTimeFilter.isBetween(from, to));
	//
	// }
	// return new TimeSeries(it);
	// }
	//
	// public <W> TimeSeries batch(long window, Function<List<Event>, Event>
	// summary) {
	// IterablePipe<Event> events = this.interval(Event::getTimeInUnix, window)
	// .map(summary);
	// return new TimeSeries(events);
	// }
	//
	// public <W> TimeSeries batchByColumn(long window,
	// Function<Iterable<DFCell>, DFCell> summary) {
	// return batch(window, new Summary.ByColumn<>(window, summary));
	// }
	//
	// public <W> TimeSeries rollBatch(long window, Function<List<Event>, Event>
	// summary) {
	// IterablePipe<Event> events = this.rollInterval(Event::getTimeInUnix,
	// window)
	// .map(summary);
	// return new TimeSeries(events);
	// }
	//
	// public <W> TimeSeries rollBatchByColumn(long window,
	// Function<Iterable<DFCell>, DFCell> summary) {
	// return rollBatch(window, new Summary.ByColumn<>(window, summary));
	// }

}
