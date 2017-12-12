package org.utilities.core.lang.iterable.timeseries;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.observer.Observer;
import org.utilities.core.lang.iterable.observer.ObserverImpl;
import org.utilities.core.lang.iterable.timeseries.filters.EventFilterTime;
import org.utilities.core.lang.iterable.timeseries.filters.EventFilterValue;
import org.utilities.core.lang.iterable.timeseries.summary.Summary;
import org.utilities.core.lang.iterable.weave.IterablePipeWoven;
import org.utilities.core.time.UtilitiesTime;
import org.utilities.core.util.lambda.LambdaValue;

public class TimeSeries<I> implements IterablePipe<Event<I>> {

	private Iterable<Event<I>> events;

	private TimeSeries(Iterable<Event<I>> events) {
		this.events = events;
	}

	public static <I, V> TimeSeries<I> newInstance(Iterable<Event<I>> events) {
		LambdaValue<Event<I>> prev = new LambdaValue<>();
		Observer<Event<I>> observer = new ObserverImpl<Event<I>>().start(() -> prev.set(null))
				.next(TimeSeries::validate, prev);
		events = IterablePipe.newInstance(events)
				.observe(observer);
		return new TimeSeries<>(events);
	}

	private static <I> void validate(Event<I> next, LambdaValue<Event<I>> prev) {
		Comparator<Event<I>> timeComparator = Event.timeComparator();
		if (prev.isPresent() && (!next.infoEquals(prev.get()) || 0 <= timeComparator.compare(prev.get(), next))) {
			// FIXME throw specific Exception
			throw new RuntimeException();
		}
		prev.set(next);
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
		return this.interval(Event::getTimeInUnix, window)
				.map(summary)
				.apply(TimeSeries::newInstance);
	}

	public <W> TimeSeries<I> batchByColumn(long window, Function<Iterable<DataValue>, DataValue> summary) {
		return batch(window, new Summary.ByColumn<>(window, summary));
	}

	public <W> TimeSeries<I> rollBatch(long window, Function<List<Event<I>>, Event<I>> summary) {
		return this.rollInterval(Event::getTimeInUnix, window)
				.map(summary)
				.apply(TimeSeries::newInstance);
	}

	public <W> TimeSeries<I> rollBatchByColumn(long window, Function<Iterable<DataValue>, DataValue> summary) {
		return rollBatch(window, new Summary.ByColumn<>(window, summary));
	}

	public static TimeSeries<String> bindColumns(Iterable<TimeSeries<String>> series, String metainfo) {
		Comparator<Event<String>> comparator = Event.timeComparator(String.class)
				.thenComparing(Event.infoComparator(String.class));
		IterablePipe<Event<String>> events = IterablePipeWoven.newInstance(comparator, series)
				.batch(Event::timeEquals)
				.flatMap(Event::bind, metainfo);
		return new TimeSeries<>(events);
	}

}
